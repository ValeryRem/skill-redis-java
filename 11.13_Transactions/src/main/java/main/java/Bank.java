package main.java;

import main.java.Account;

import java.util.*;

public class Bank extends Thread {
    private final static HashMap<Integer, Account> accounts = new HashMap();
    private final static long sumToCheck = 50000;
    private final Integer fromAccountNum;
    private final Integer toAccountNum;
    private final long amount;
    private final static int numberOfAccounts = 1000;
    private long totaBalanceBefore = getTotalBalance();


    public Bank(Integer fromAccountNum, Integer toAccountNum, long amount) {
        this.fromAccountNum = fromAccountNum;
        this.toAccountNum = toAccountNum;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": ");
        try {
            transfer(fromAccountNum, toAccountNum, amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isFraud(Integer fromAccountNum, Integer toAccountNum, long amount)
            throws InterruptedException {
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
    public synchronized void transfer(Integer fromAccountNum, Integer toAccountNum, long amount)
            throws InterruptedException {
        Account account1 = accounts.get(fromAccountNum);
        Account account2 = accounts.get(toAccountNum);
        if (!fromAccountNum.equals(toAccountNum)) {
            if (isFraud(fromAccountNum, toAccountNum, amount)) {
                account1.setOpen(false);
                account2.setOpen(false);
                Thread.sleep(2000);
                System.out.println("Accounts ## " + account1.getAccNumber() + " & " + account2.getAccNumber() + " suspended for fraud-check!");
                account1.setOpen(true);
                account2.setOpen(true);
                System.out.println("Accounts ## " + account1.getAccNumber() + " & " + account2.getAccNumber() + " passed fraud-check OK.");
            }
            if (account1.isOpen() && account1.getBalance() > amount) {
                account1.setBalance(account1.getBalance() - amount);
                if (account2.isOpen()) {
                    account2.setBalance(account2.getBalance() + amount);
                }
                System.out.println("Transfer of " + amount + " RUR from main.java.Account #" + account1.getAccNumber() + " to main.java.Account #" + account2.getAccNumber() +
                        " fulfilled successfully. \nNew balances: main.java.Account #" + account1.getAccNumber() + ": " + account1.getBalance() + " RUR; " +
                        "main.java.Account #" + account2.getAccNumber() + ": " + account2.getBalance() + " RUR.\n");
            } else {
                System.out.println("Transfer impossible. Too few money at account # " + account1.getAccNumber() + ".\n");
            }
        } else {
            System.out.println("Recursive transfer is impossible.");
        }
    }

    public static synchronized void getHashMapOfAccounts() {

        for (int i = 1; i <= numberOfAccounts; i++){
            Integer accNumber = i;
            Account account = new Account (accNumber, (long) (100000*Math.random()),  true);
            accounts.put(accNumber, account);
        }
    }

    public static long getTotalBalance () {
        Collection<Account> set = accounts.values();
        long result = 0;
        for (Account account : set) {
            result += account.getBalance();
        }
        return result;
    }

    public static HashMap<Integer, Account> getAccounts() {
        return accounts;
    }
}
