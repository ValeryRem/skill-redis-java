package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Написать программу, которая будет измерять размер всего содержимого папки, путь которой передаётся на вход,
 * и выводить его в удобочитаемом виде — в байтах, килобайтах, мегабайтах или гигабайтах.
 */
public class Main {

    public static void main(String[] args) {
        File dir = new File("C:/Users/valery/Desktop/Android");
        System.out.println("Size of the directory C:/Users/valery/Desktop/Android: " + getDirSize(dir)/1000000 + " mb");
    }

    private static long getDirSize(File dir) {
        long size = 0;
        if (dir.isFile()) {
            size = dir.length();
        } else {
            File[] subFiles = dir.listFiles();
            try {
                for (File file : subFiles) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getDirSize(file);
                    }
                }
            }
            catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return size;
    }
}
