import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private final char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    private final char firstLetter;
    public long start = System.currentTimeMillis();

    public Task(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public void run() {
        try
        {
            generateCarNumber(firstLetter);
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private synchronized void generateCarNumber(char firstLetter) throws Exception {
        int regionCode = 100;
        String fileNameRoot = "res/numbers_";
        PrintWriter printWriter = new PrintWriter(fileNameRoot + firstLetter + ".txt");
        StringBuilder stringBuilder = new StringBuilder();
            for (int regionIncrement = 0; regionIncrement < 5; regionIncrement++) {
                for (int number = 1; number < 1000; number++) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                stringBuilder.append(firstLetter);
                                stringBuilder.append(padNumber(number, 3));
                                stringBuilder.append(secondLetter);
                                stringBuilder.append(thirdLetter);
                                stringBuilder.append(padNumber(regionCode + regionIncrement, 2));
                                stringBuilder.append("\n");
                            }
                        }
                }
        }
        printWriter.write(stringBuilder.toString());
        printWriter.flush();
        printWriter.close();
        System.out.println(("Task " + firstLetter + ": " + Thread.currentThread().getName()) + ": " + (System.currentTimeMillis() - start) + " ms");

        try (OutputStream out = Files.newOutputStream(Path.of(fileNameRoot + ".txt"),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
                Files.copy(Paths.get(fileNameRoot + firstLetter + ".txt"), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String padNumber(int number, int numberLength) {
        StringBuilder builder = new StringBuilder();
        int padSize = numberLength - String.valueOf(number).length();
        builder.append("0".repeat(Math.max(0, padSize)));
        builder.append(number);
        return builder.toString();
    }

}
