package ru.job4j;





import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class Wget implements Runnable {
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
        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream());
            fout = new FileOutputStream(filename);
            byte[] buffer = new byte[1024];
            int count;
            long totalBytes = 0;
            long startTime = System.currentTimeMillis();

            while ((count = in.read(buffer, 0, buffer.length)) != -1) {
                long chunkStart = System.nanoTime();
                fout.write(buffer, 0, count);
                totalBytes += count;

                // Ограничение скорости
                long chunkTime = System.nanoTime() - chunkStart;
                double chunkTimeMs = chunkTime / 1_000_000.0;
                double currentSpeed = count / chunkTimeMs;

                if (currentSpeed > speed) {
                    long requiredTime = (long)(count / (double)speed);
                    long sleepTime = requiredTime - (long)chunkTimeMs;
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                }
            }
            System.out.println("Загрузка завершена : " + totalBytes);

        } catch (MalformedURLException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                System.err.println("ошибка"  + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            System.out.println("Usage: java Wget <URL> <filename> <course_test>");
            System.out.println("https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml");
            return;
        }

        String url = args[0];
        String filename = args[1];
        int speed = Integer.parseInt(args[2]);

        if (speed <= 0) {
            System.out.println("Error: Speed must be positive");
            return;
        }

        Thread wget = new Thread(new Wget(url, filename, speed));
        wget.start();
        wget.join();
    }
}