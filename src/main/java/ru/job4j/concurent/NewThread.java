package ru.job4j.concurent;

public class NewThread {
    public static void main(String[] args) throws InterruptedException {
                Thread t1 = new Thread(() -> {
                    try {
                        for (int i = 0; i <= 100 && !Thread.currentThread().isInterrupted(); i += 10) {
                            System.out.print("\rLoading: " + i + "%");
                            Thread.sleep(1000);
                        }
                        if (!Thread.currentThread().isInterrupted()) {
                            System.out.println("\rLoading: 100% - Завершено!");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("\rЗагрузка прервана на " + Thread.currentThread().getName());
                        Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                    }
                }, "ProgressThread");
                Thread t2 = new Thread(() -> {
                    char[] spinner = {'-', '\\', '|', '/'};
                    int index = 0;
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.print("\rLoading... " + spinner[index]);
                            index = (index + 1) % spinner.length;
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("\rЗагрузка завершена!");
                        Thread.currentThread().interrupt(); // Восстанавливаем статус
                    }
                }, "SpinnerThread");
                t1.start();
                t2.start();
                Thread.sleep(100000);
                t1.interrupt();
                t2.interrupt();
                t1.join();
                t2.join();
            }
        }


