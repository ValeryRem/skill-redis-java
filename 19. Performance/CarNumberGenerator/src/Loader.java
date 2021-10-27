import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Loader {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

//        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        PrintWriter printWriter = new PrintWriter("res/numbers.txt");
        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        int regionCode = 199;
        for (int regions = 1; regions < 100; regions++) {
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
        System.out.println((System.currentTimeMillis() - start) + " ms");
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
