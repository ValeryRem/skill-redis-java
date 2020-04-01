package com.company;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileCopyVisitor extends SimpleFileVisitor<Path> {
    private Path source, destination;

    public FileCopyVisitor(Path s, Path d) {
        source = s;
        destination = d;
    }

    @Override
    public FileVisitResult visitFile(Path path,
                                     BasicFileAttributes fileAttributes) {
        Path newd = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path,
                                             BasicFileAttributes fileAttributes) {
        Path newd = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

}
