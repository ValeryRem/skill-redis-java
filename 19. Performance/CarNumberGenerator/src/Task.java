import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private String name;

    public Task(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run()
    {
        try
        {
            generateCarNumber();
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void generateCarNumber() throws Exception{
        long start = System.currentTimeMillis();
        PrintWriter printWriter = new PrintWriter("res/numbers.txt");
        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        int regionCode = 199;
        for (int regions = 1; regions < 10; regions++) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
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
        System.out.println((getName() + ": " + Thread.currentThread().getName() + ": " + (System.currentTimeMillis() - start) + " ms"));
    }

    private static String padNumber(int number, int numberLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(number);
        int padSize = numberLength - builder.toString().length();
        for (int i = 0; i < padSize; i++) {
            builder.append("0");
            builder.append(padSize - numberLength);
        }
        return builder.toString();
    }
}
