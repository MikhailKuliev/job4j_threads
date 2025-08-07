package ru.job4j.concurent;
public class ConsoleProgress {
    public static void main(String[] args) throws InterruptedException {
        Thread r1 = new Thread(() -> {
            char[] process = new char[]{'-', '\\', '|', '/'};
            int count = 0;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\rLoading..." + process[count]);
                    count = (count + 1) % process.length;

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.print("\rЗагрузка Завершена! ");
            }
        });
        r1.start();
        Thread.sleep(5000);
        r1.interrupt();

    }
}