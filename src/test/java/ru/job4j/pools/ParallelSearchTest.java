package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

class ParallelSearchTest {

    @Test
    void searchWithIntegerArrayShouldFindElement() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer target = 6;

        ParallelSearch<Integer> task = new ParallelSearch<>(array, target, 0, array.length);
        int result = new ForkJoinPool().invoke(task);

        assertEquals(5, result);
    }

    @Test
    void searchWithStringArrayShouldFindElement() {
        String[] array = {"cherry", "apple", "orange", "pineapple"};
        String target = "apple";

        ParallelSearch<String> task = new ParallelSearch<>(array, target, 0, array.length);
        int result = new ForkJoinPool().commonPool().invoke(task);

        assertEquals(1, result);
    }

    @Test
    void searchWithSmallArrayShouldUseLinearSearch() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer target = 3;
        ParallelSearch<Integer> task = new ParallelSearch<>(array, target, 0, array.length);
        int result = new ForkJoinPool().invoke(task);
        assertEquals(2, result);

    }

    @Test
    void searchWithLargeArrayShouldUseRecursiveSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        Integer target = 20;
        ParallelSearch<Integer> task = new ParallelSearch<>(array, target, 0, array.length);
        int result = new ForkJoinPool().invoke(task);
        assertEquals(19, result);

    }

    @Test
    void searchWhenElementNotInArrayShouldReturnNegativeOne() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer target = 101;
        ParallelSearch<Integer> task = new ParallelSearch<>(array, target, 0, array.length);
        int result = new ForkJoinPool().invoke(task);
        assertEquals(-1, result);

    }
}