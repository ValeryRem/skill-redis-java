/**
 * Написать код парсинга банковской выписки (файл movementsList.csv). Код должен выводить сводную информацию
 * по этой выписке: общий приход, общий расход, а также разбивку расходов по назначению платежей.
 *         String csvFile = "C:\\Users\\valery\\Desktop\\java_basics\\09_BankParsing\\src\\com\\movementList1.csv";
 *         String orgFile = "C:\\Users\\valery\\Desktop\\java_basics\\09_BankParsing\\src\\com\\taksist.org";
 */
package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Input the name of file to parse!");
        Scanner scanner = new Scanner(System.in);
        String inputFileName = scanner.nextLine();
        scanner.close();
        try {
            var list = Parsers.parseFile(inputFileName);
            Transaction.setTransactionList(list);
            Processing.printSumOfIncome(list);
            Processing.printSumOfExpenses(list);
            Processing.printSummaryByGroups(list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
