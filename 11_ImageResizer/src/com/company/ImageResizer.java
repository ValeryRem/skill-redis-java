package com.company;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ImageResizer {
    private static int maxSize = 400;
    private static String srcFolder = "C:/Users/valery/Desktop/java_basics/11_ImageResizer/src/resources";
    private static String dstFolder = "C:/Users/valery/Desktop/java_basics/11_ImageResizer/src/resized";

    public static void main(String[] args) throws IOException {
        Instant begin = Instant.now();
        File srsDir = new File(srcFolder);
//        long start = System.currentTimeMillis();
        File[] files = srsDir.listFiles();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        if (files == null) {
            System.out.println("Directory of images is empty!");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                Helper resizer = new Helper(dstFolder, files[i], maxSize);
                executor.execute(resizer);
            }
        }
        executor.shutdown();
        if (executor.isShutdown()) {
            Instant end = Instant.now();
            System.out.println("Duration of code running: " + Duration.between(begin, end));
        }
    }
//        int middle = files.length / 2;
//        File[] files1 = new File[middle];
//        System.arraycopy(files, 0, files1, 0, files1.length);
//        Helper resizer1 = new Helper(files1, newWidth, newHeight, dstFolder, start);
//        Thread thread1 = new Thread(resizer1, "t1");
//        thread1.start();
//        try {
//            thread1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        File[] files2 = new File[files.length - middle];
//        System.arraycopy(files, middle, files2, 0, files2.length);
//        Helper resizer2 = new Helper(files2, newWidth, newHeight, dstFolder, start);
//        Thread thread2 = new Thread(resizer2, "t2");
//        thread2.start();
}
