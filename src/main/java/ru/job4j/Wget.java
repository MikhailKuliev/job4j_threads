package ru.job4j;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    private final String url;
    private final String filename;
    private final int speed;

    public Wget(String url, String filename, int speed) {
        this.url = url;
        this.filename = filename;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (var in = new BufferedInputStream(new URL(url).openStream());
             var fout = new FileOutputStream(filename)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            long totalBytes = 0;
            long startTime = System.currentTimeMillis();

            while ((bytesRead = in.read(buffer)) != -1) {
                long chunkStart = System.nanoTime();
                fout.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;

                long chunkTime = System.nanoTime() - chunkStart;
                double chunkTimeMs = chunkTime / 1_000_000.0;
                double currentSpeed = bytesRead / chunkTimeMs;

                if (currentSpeed > speed) {
                    long requiredTime = (long) (bytesRead / (double) speed);
                    long sleepTime = requiredTime - (long) chunkTimeMs;
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                }
            }
            System.out.println("Загрузка завершена: " + totalBytes + " байт");
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            System.out.println("Usage: java Wget <URL> <filename> <speed_KBps>");
            System.out.println("Пример: https://example.com/file.zip output.zip 100");
            return;
        }

        String url = args[0];
        String filename = args[1];
        int speed = Integer.parseInt(args[2]);

        if (speed <= 0) {
            System.out.println("Скорость должна быть > 0");
            return;
        }

        Thread wget = new Thread(new Wget(url, filename, speed));
        wget.start();
        wget.join();
    }
}