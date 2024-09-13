package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String output;

    public Wget(String url, int speed, String output) {
        this.url = url;
        this.speed = speed;
        this.output = output;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(output);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int bytesReadCount = 0;
            long start = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, bytesReadCount, dataBuffer.length - bytesReadCount)) != -1) {
                bytesReadCount += bytesRead;
                output.write(dataBuffer, 0, bytesRead);
                if (bytesReadCount == speed) {
                    long time = System.currentTimeMillis() - start;
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                    }
                    System.out.println(bytesReadCount + " bytes written : " + "time - " + (System.currentTimeMillis() - start));
                    bytesReadCount = 0;
                }
            }
            System.out.println(bytesReadCount + " bytes written : " + "time - " + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        if (args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty()) {
            throw new IllegalArgumentException("Wrong arguments");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String output = args[2];
        Thread wget = new Thread(new Wget(url, speed, output));
        wget.start();
        wget.join();
    }
}
