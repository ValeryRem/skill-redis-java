package com.company;

        import java.io.File;
        import java.io.IOException;
        import java.nio.file.*;

        import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

/**
 * 1) Написать программу, которая будет измерять размер всего содержимого папки, путь которой передаётся на вход,
 * и выводить его в удобочитаемом виде — в байтах, килобайтах, мегабайтах или гигабайтах.
 * For example: C:/Users/valery/Desktop/Android
 * 2) Написать код, который будет копировать указанную папку с файлами с сохранением структуры в другую указанную папку.
 */

public class Main {
    static String rootDirectory = "C:/Users/valery/Desktop/Разное/Уроки внуков/";
    static String resultDirectory = "C:/Users/valery/Desktop/Разное/Уроки внуков - copy/";

    public static void main(String[] args) {
        File rootFile = new File(rootDirectory);
        try {
            copyFiles(rootFile, resultDirectory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void copyFiles(File rootFile, String fileTo) throws IOException {
        new File(fileTo);
        File[] files = rootFile.listFiles();
        Files.copy(Path.of(rootFile.getAbsolutePath()), Path.of(fileTo), COPY_ATTRIBUTES);
        String copyName;
        if (files == null) {
            System.out.println("Root file is empty.");
        }
        for (File file : files) {
            copyName = resultDirectory + file.getAbsolutePath().substring(rootDirectory.length());
            Path pathTo = Path.of(copyName);
            try {
                if (file.isDirectory()) {
                    copyFiles(file, copyName);
                } else {
                    Files.copy(Paths.get(file.getAbsolutePath()), pathTo, COPY_ATTRIBUTES);
                }
                System.out.println(copyName);
            } catch (FileAlreadyExistsException e) {
                System.err.println("The directory [" + copyName + "] already exists.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
