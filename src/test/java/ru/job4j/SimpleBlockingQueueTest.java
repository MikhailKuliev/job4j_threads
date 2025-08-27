package ru.job4j;


import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
    final CopyOnWriteArrayList<Integer>buffer = new CopyOnWriteArrayList<>();
    final SimpleBlockingQueue<Integer>queue = new SimpleBlockingQueue<>(5);

    Thread producer = new Thread(() -> {
      IntStream.range(0, 5).forEach(value -> {
         try {
             queue.offer(value);

         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
         }
      });
    });

    Thread consumer = new Thread(() -> {
       while (!(queue.size() ==0) || !Thread.currentThread().isInterrupted()) {
           try {
               buffer.add(queue.poll());
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
           }
       }
    });
    producer.start();
    consumer.start();

    producer.join();

    consumer.interrupt();

    consumer.join();

    assertThat(buffer.toArray(), is(new Integer[]{0, 1, 2, 3, 4}));
    }
    }