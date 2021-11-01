import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Loader {
    private static List<String> filenameList = new LinkedList<>();
    public static void main(String[] args) {
        long start = new Task().start;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        for (int i = 1; i <= 4; i++) {
            Task task = new Task(i);
            filenameList.add(task.getFileNameRoot() + i + ".txt");
            executor.execute(task);
        }
        executor.shutdown();

        try(OutputStream out = Files.newOutputStream(Paths.get(new Task().getFileNameRoot()  + ".txt"),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            for (String inFileName : filenameList) {
                Files.copy(Paths.get(inFileName), out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Проверяем завершение записи частных резултатов в полный файл и выводим общее время
        if(new Task().checkFile()) {
            System.out.println(("Total duration: " +  (System.currentTimeMillis() - start) + " ms"));
        }
    }
}
