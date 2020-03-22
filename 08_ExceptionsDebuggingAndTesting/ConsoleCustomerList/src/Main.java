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
        System.out.println("Input order from the commands available: \n"  + commandExamples);
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        while (true) {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);
            try {
                switch (tokens[0]) {
                    case "add":
                        executor.addCustomer(tokens[1]);
                        System.out.println("Customer " + tokens[1] + " is put to the list.");
                        break;
                    case "list":
                        executor.listCustomers();
                        break;
                    case "remove":
                        executor.removeCustomer(tokens[1]);
                        System.out.println("Customer " + tokens[1] + " is deleted.");
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
