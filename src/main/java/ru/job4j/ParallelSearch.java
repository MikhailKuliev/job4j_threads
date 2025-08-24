package ru.job4j;

import java.util.concurrent.CountDownLatch;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final CountDownLatch producerFinished = new CountDownLatch(1);
        final CountDownLatch consumerStart = new CountDownLatch(1);
        final Thread consumer = new Thread(
                () -> {
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
                }

                );
        consumer.start();

        final Thread producer = new  Thread(
                () -> {
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
                    }

        );
        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
    }
}
