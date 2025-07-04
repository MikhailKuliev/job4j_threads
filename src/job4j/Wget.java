package ru.job4j;

public class Wget {
    public static void main(String[] args) {

        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i <= 100; i += 1) {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(1000);
                        }
                        System.out.println( " Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}