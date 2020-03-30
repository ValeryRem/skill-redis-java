package com.company;

import java.io.File;
import java.util.Scanner;

/**
 * Написать программу, которая будет измерять размер всего содержимого папки, путь которой передаётся на вход,
 * и выводить его в удобочитаемом виде — в байтах, килобайтах, мегабайтах или гигабайтах.
 * For example: C:/Users/valery/Desktop/Android
 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final long MEGABYTE = 1000000;
    static final long GIGABYTE = MEGABYTE*1000;
    public static void main(String[] args) {
        System.out.println("Input path to your directory");
        String path = scanner.nextLine();
        File dir = new File(path);
        long size = getDirSize(dir);
        System.out.println("Size of the directory " + path + ": ");
        if (size <= MEGABYTE) {
            System.out.println(size/1000 + " Kb");
        } else if (size > MEGABYTE && size <= GIGABYTE) {
            System.out.println(size/MEGABYTE + " Mb");
        } else {
            System.out.println(size/GIGABYTE + " Gb");
        }
    }

    private static long getDirSize(File dir) {
        long size = 0;
        if (dir.isFile()) {
            size = dir.length();
        } else {
            File[] subFiles = dir.listFiles();
            if (subFiles != null) {
                try {
                    for (File file : subFiles) {
                        if (file.isFile()) {
                            size += file.length();
                        } else {
                            size += getDirSize(file);
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("The directory is absent.");
            }
        }
        return size;
    }
}
