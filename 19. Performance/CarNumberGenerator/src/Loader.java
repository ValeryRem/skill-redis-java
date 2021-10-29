import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Loader {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

//        ExecutorService executor = Executors.newScheduledThreadPool(2);
//        executor.submit(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(100);
//                generateCarNumber();
//            }
//            catch (Exception e) {
//                System.err.println("task interrupted");
//            }
//        });
//        executor.shutdown();

        Runnable task = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                generateCarNumber();
            }
            catch (Exception e) {
                System.err.println("task interrupted");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 10, TimeUnit.MILLISECONDS);
//        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
    }

    private static void generateCarNumber() throws Exception{
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
//                        String carNumber = firstLetter + padNumber(number, 3) +
//                            secondLetter + thirdLetter + padNumber(regionCode, 2);
//                        writer.write(carNumber.getBytes());
//                        writer.write('\n');
//                            if (stringBuilder.length() > 1024) {
//                                printWriter.write(stringBuilder.toString());
//                                stringBuilder = new StringBuilder();
//                            }
                        }
                    }
                }
            }
        }
        printWriter.flush();
        printWriter.close();
        System.out.println((Thread.currentThread().getName() + ": " + (System.currentTimeMillis() - start) + " ms"));
    }

    private static String padNumber(int number, int numberLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(number);
//        String numberStr = Integer.toString(number);
//        int padSize = numberLength - numberStr.length();
        int padSize = numberLength - builder.toString().length();
        for (int i = 0; i < padSize; i++) {
            builder.append("0");
            builder.append(padSize - numberLength);
//            numberStr = '0' + numberStr;
        }
        return builder.toString();
//        return numberStr;
    }
}
