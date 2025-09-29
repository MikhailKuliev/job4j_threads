package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch <T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T target;
    private final int from;
    private final int to;
    private final static int THRESHOLD = 10;

    public ParallelSearch(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            return linearSearch();
        }
        int middle = (from + to) / 2;

        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, target, from, middle);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, target, middle, to);

        leftSearch.fork();
        rightSearch.fork();

        int leftResult = leftSearch.join();
        int rightResult = rightSearch.join();

        return Math.max(leftResult, rightResult);

    }

    private int linearSearch() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(target)) {
                return i;

            }
        }
        return -1;
    }
}