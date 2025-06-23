package ru.job4j.concurent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        // первый поток с автомат именем
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();// второй поток second
        Thread second = new Thread(() -> System.out.println(Thread.currentThread().getName()),
                "second"
        );
        second.start();
        //  главный поток
        System.out.println(Thread.currentThread().getName());

    }
}





