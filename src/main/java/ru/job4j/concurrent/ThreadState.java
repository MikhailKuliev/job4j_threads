package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()),
                "first"//1 нить


        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()),

                "second"  // 2 нить
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) { //ЦИКЛ ПРОВЕРКИ
            System.out.println(first.getState());
        } while (second.getState() != Thread.State.TERMINATED) { //ЦИКЛ ПРОВЕРКИ
                System.out.println(second.getState());
        }


        System.out.println(first.getState());

        System.out.println(second.getState());//ВЫВЕДЕТ TER НИТЬ ЗАВЕРШЕНА
    }
}