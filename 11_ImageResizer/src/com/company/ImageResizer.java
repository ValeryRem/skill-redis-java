package com.company;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ImageResizer {
    private static int newWidth = 400;
    private static int delaySec = 2;
    private static String srcFolder = "C:/Users/valery/Desktop/java_basics/11_ImageResizer/src/resources";
    private static String dstFolder = "C:/Users/valery/Desktop/java_basics/11_ImageResizer/src/resized";


    public static void main(String[] args) throws IOException {

//        Instant begin = Instant.now();
        File srsDir = new File(srcFolder);
        long start = System.currentTimeMillis();
        File[] files = srsDir.listFiles();

        if (files == null) {
            System.out.println("Directory of images is empty!");
            return;
        }
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
        Helper resizer = new Helper(files, newWidth, delaySec, dstFolder, start);
        executor.scheduleWithFixedDelay(resizer, delaySec, delaySec, TimeUnit.SECONDS);
        executor.execute(resizer);
        executor.shutdown();
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
//        Instant end = Instant.now();
//        System.out.println("Duration of code running: " + Duration.between(begin, end));
}
