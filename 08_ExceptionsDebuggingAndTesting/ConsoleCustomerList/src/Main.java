import java.util.IllegalFormatException;
import java.util.Scanner;

public class Main {
    private static String addCommand = "add Василий Петров vasily.petrov@gmail.com +79215637722";
    private static String commandExamples = "\t" + addCommand + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static String commandError = "Wrong command! Available command examples: \n" +
            commandExamples;
    private static String helpText = "Command examples:\n" + commandExamples;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        while (true) {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);

            switch (tokens[0]) {
                case "add":
                    try {
                        if (executor.addCustomer(tokens[1])) {
                            System.out.println("Customer " + tokens[1] + " is put to the list.");
                        } else {
                            System.out.println("Wrong format of the phone number input!");
                            continue;
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Too short input!");
                    }
                    break;
                case "list":
                    executor.listCustomers();
                    break;
                case "remove":
                    try {
                        executor.removeCustomer(tokens[1]);
                    } catch (NullPointerException ex) {
                        ex.getMessage();
                    }
                    break;
                case "count":
                    System.out.println("There are " + executor.getCount() + " customers");
                    break;
                case "help":
                    System.out.println(helpText);
                    break;
                default:
                    System.out.println(commandError);
            }
        }
    }
}
