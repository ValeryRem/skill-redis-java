package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static String rootDirectory = "C:/Users/valery/Desktop/Разное/Уроки внуков/";
    static String resultDirectory = "C:/Users/valery/Desktop/Разное/Уроки внуков - copy/";

    public static void main(String[] args) {
        Path rootPath = Path.of(rootDirectory);
        Path resultPath = Path.of(resultDirectory);
        try {
            Path p = Files.walkFileTree(rootPath, new FileCopyVisitor(rootPath, resultPath));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
