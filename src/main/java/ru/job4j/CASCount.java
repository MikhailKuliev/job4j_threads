package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        int next;
        do {
            ref = count.get();
            next = ref + 1;

        } while (!count.compareAndSet(ref, next));
    }

    public int get() {
    return count.get();
    }
}