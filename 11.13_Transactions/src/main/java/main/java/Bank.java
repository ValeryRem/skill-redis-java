package main.java;

import java.util.*;

public class Bank{
    private volatile HashMap<Integer, Account> accounts;
    private final static long sumToCheck = 50000;
    private String output;
    private boolean positiveCurrentBalanceIndicator = true;

    public Bank(HashMap<Integer, Account> accounts) {
        this.accounts = accounts;
    }
//    @Override
//    public void run() {
//        Integer fromAccountNum = (int) (Math.random()*100 + 1);
//        Integer toAccountNum = (int) (Math.random()*100 + 1);
//        long amount = (long) (100000*Math.random());
////        System.out.println(Thread.currentThread().getName() + ": ");
//        try {
//            transfer(fromAccountNum, toAccountNum, amount);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public synchronized boolean isFraud(Integer fromAccountNum, Integer toAccountNum, long amount) {
        boolean result = false;
        if(amount >= sumToCheck) {
            double indx = Math.random();
            if (indx <= 0.30) {
                result = true;
            }
        }
        return result;
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(Integer fromAccountNum, Integer toAccountNum, long amount)
            throws InterruptedException {
        System.out.printf("%s: start sending %d -> %d: %d RUB%n",
                Thread.currentThread().getName(), fromAccountNum, toAccountNum, amount);
        Account account1 = accounts.get(fromAccountNum);
        Account account2 = accounts.get(toAccountNum);
//        if (!fromAccountNum.equals(toAccountNum)) {
            if (isFraud(fromAccountNum, toAccountNum, amount)) {
                account1.setOpen(false);
                account2.setOpen(false);
                System.out.println("Accounts ## " + account1.getAccNumber() + " & " + account2.getAccNumber() + " suspended for fraud-check!");
                Thread.sleep(2000);
                account1.setOpen(true);
                account2.setOpen(true);
                System.out.println("Accounts ## " + account1.getAccNumber() + " & " + account2.getAccNumber() + " passed fraud-check OK.");
            }
            if (account1.isOpen() && account2.isOpen()) {
                if (account1.getAccNumber() < account2.getAccNumber()) {
                    synchronized (account1) {
                        synchronized (account2) {
                            doTransfer(amount, account1, account2);
                        }
                    }
                } else if (account1.getAccNumber() > account2.getAccNumber()) {
                    synchronized (account2) {
                        synchronized (account1) {
                            doTransfer(amount, account1, account2);
                        }
                    }
                } else {
                    output = "Recursive transfer is impossible!";
                    System.out.println(output);
                }
            } else {
                System.out.println("The Accounts are closed for operations!");
            }

        System.out.printf("%s: end sending %d -> %d: %d RUB%n",
                Thread.currentThread().getName(), fromAccountNum, toAccountNum, amount);
    }

    private void doTransfer(long amount, Account account1, Account account2) throws InterruptedException {
        if (account1.getBalance() > amount) {
            Thread.sleep(1000);
            account1.setBalance(account1.getBalance() - amount);
            account2.setBalance(account2.getBalance() + amount);
            if (account1.getBalance() < 0) {
                positiveCurrentBalanceIndicator = false;
            }
        } else {
            System.out.println("Transfer impossible. Too few money at account # " + account1.getAccNumber());
        }
        System.out.println("Transfer of " + amount + " RUR from main.java.Account #" + account1.getAccNumber() + " to main.java.Account #" + account2.getAccNumber() +
                " fulfilled successfully. \nNew balances: main.java.Account #" + account1.getAccNumber() + ": " + account1.getBalance() + " RUR; " +
                "main.java.Account #" + account2.getAccNumber() + ": " + account2.getBalance() + " RUR.\n");
    }


    public long getTotalBalance (HashMap<Integer, Account> accounts) {
        Collection<Account> set = accounts.values();
        long result = 0;
        for (Account account : set) {
            result += account.getBalance();
        }
        return result;
    }

    public HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public String getOutput() {
        return output;
    }

    public boolean isPositiveCurrentBalanceIndicator() {
        return positiveCurrentBalanceIndicator;
    }
}
