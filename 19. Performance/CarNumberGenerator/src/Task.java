import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private final String fileNameRoot = "res/numbers_";
    private int numebrOfTask;
    private String taskName;
    private final char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    public long start = System.currentTimeMillis();
    public Task() {}

    public Task(int numebrOfTask)
    {
        this.numebrOfTask = numebrOfTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setNumebrOfTask(int numebrOfTask) {
        this.numebrOfTask = numebrOfTask;
    }

    public String getFileNameRoot() {
        return fileNameRoot;
    }

    @Override
    public void run() {
        try
        {
            generateCarNumber(numebrOfTask);
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void generateCarNumber(int i) throws Exception {
//        long start = System.currentTimeMillis();
        PrintWriter printWriter = new PrintWriter(fileNameRoot + i + ".txt");
        char[] subset = {letters[3*(i-1)], letters[1+3*(i-1)], letters[2+3*(i-1)]};
        int regionCode = 199;
        for (int regions = 1; regions < 10; regions++) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : subset) {
                    for (char secondLetter : subset) {
                        for (char thirdLetter : subset) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(firstLetter);
                            stringBuilder.append(padNumber(number, 3));
                            stringBuilder.append(secondLetter);
                            stringBuilder.append(thirdLetter);
                            stringBuilder.append(padNumber(regionCode, 2));
                            stringBuilder.append("\n");
                            printWriter.write(stringBuilder.toString());
                        }
                    }
                }
            }
        }
        printWriter.flush();
        printWriter.close();


        System.out.println(("Task " + i + ": " + Thread.currentThread().getName()) + ": " + (System.currentTimeMillis() - start) + " ms");
    }

    private String padNumber(int number, int numberLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(number);
        int padSize = numberLength - builder.toString().length();
        for (int i = 0; i < padSize; i++) {
            builder.append("0");
            builder.append(padSize - numberLength);
        }
        return builder.toString();
    }

    public boolean checkFile () {
        return  new File(getFileNameRoot()  + ".txt").isFile();
    }
}
