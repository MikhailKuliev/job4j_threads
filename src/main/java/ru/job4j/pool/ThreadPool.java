package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    private volatile boolean isShutdown = false;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.interrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;

                    }
                }

            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException();
        }
        tasks.offer(job);
    }

    public void shutdown() {
        isShutdown = true;
        threads.forEach(Thread::interrupt);

    }
}