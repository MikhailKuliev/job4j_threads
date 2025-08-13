package ru.job4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

class SimpleBlockingQueueTest {
    @Test
    void whenProducerAddsElementThenConsumerReceivesIt() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final int testValue = 42;
        final int[] receivedValue = new int[1];

        Thread producer = new Thread(() -> {
            queue.offer(testValue);

        });
        Thread consumer = new Thread(() -> {
           try {
               receivedValue[0] = queue.poll();
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
           }
        });
        consumer.start();
        Thread.sleep(100);
        producer.start();

        producer.join();
        consumer.join();

        Assertions.assertEquals(testValue, receivedValue[0]);

    }

    @Test
    void whenQueueIsEmptyThenConsumerWaits() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>();
        AtomicBoolean consumerFinished = new AtomicBoolean(false);

   Thread consumer = new Thread(() -> {
       try {
           queue.poll();
           consumerFinished.set(true);
       } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
       }
   });
   consumer.start();
   Thread.sleep(200);
   Assertions.assertFalse(consumerFinished.get());
   Assertions.assertEquals(Thread.State.WAITING, consumer.getState());

   consumer.interrupt();
   consumer.join();

    }

}
