package ru.job4j;

import java.util.concurrent.CountDownLatch;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final CountDownLatch producerFinished = new CountDownLatch(1);
        final CountDownLatch consumerStart = new CountDownLatch(1);

        Thread consumer = createConsumer(queue, producerFinished, consumerStart);
        Thread producer = createProducer(queue, producerFinished);

        consumer.start();
        consumerStart.await();

        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    }

    private static Thread createConsumer(SimpleBlockingQueue<Integer> queue,
                                         CountDownLatch producerFinished,
                                         CountDownLatch consumerStart) {
        return new Thread(() -> {
            consumerStart.countDown();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Integer item = queue.poll();
                    if (item == null && producerFinished.getCount() == 0) {
                        break;
                    }
                    if (item != null) {
                        System.out.println(item);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private static Thread createProducer(SimpleBlockingQueue<Integer> queue,
                                         CountDownLatch producerFinished) {
        return new Thread(() -> {
            try {
                for (int index = 0; index != 3; index++) {
                    queue.offer(index);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                producerFinished.countDown();
            }
        });
    }
}