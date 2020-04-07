/**
 * Написать код парсинга банковской выписки (файл movementsList.csv). Код должен выводить сводную информацию
 * по этой выписке: общий приход, общий расход, а также разбивку расходов по назначению платежей.
 *         String csvFile = "C:\\Users\\valery\\Desktop\\java_basics\\09_BankParsing\\src\\com\\movementList1.csv";
 *         String orgFile = "C:\\Users\\valery\\Desktop\\java_basics\\09_BankParsing\\src\\com\\taksist.org";
 */
package com.company;
import java.util.*;

import static com.company.Transaction.getTransactionList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Input the name of file to parse!");
        Scanner scanner = new Scanner(System.in);
        String inputFileName = scanner.nextLine();
        scanner.close();
        try {
            Transaction.setTransactionList(Parsers.parseFile(inputFileName));
            Processing.printSumOfIncome(getTransactionList());
            Processing.printSumOfExpenses(getTransactionList());
            Processing.printSummaryByGroups(getTransactionList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
