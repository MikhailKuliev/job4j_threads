package ru.job4j.concurent;

public class NewThread {
    public static void main(String[] args) throws InterruptedException {

                // Поток 1: прогресс-бар с %
                Thread T1 = new Thread(() -> {
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

                // Поток 2: спиннер
                Thread T2 = new Thread(() -> {
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

                T1.start();
                T2.start();

                // Ждём 5 секунд и прерываем оба потока
                Thread.sleep(100000);
                T1.interrupt();
                T2.interrupt();

                // Ожидаем завершения (необязательно, но полезно для порядка)
                T1.join();
                T2.join();
            }
        }


