package main.java;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static HashMap<Integer, Account> accounts = new HashMap();
    public static void main(String[] args) {
        transferAll();
    }

    public static void transferAll() {
        int numberOfTransfers = 10;
        int numberOfAccounts = 100;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        getHashMapOfAccounts(numberOfAccounts);
        Bank bank = new Bank(accounts);
        System.out.println("Init total balance: " +  bank.getTotalBalance(accounts));
        for (int i = 1; i <= numberOfTransfers; i++) {
            Integer fromAccountNum = (int) (Math.random()*100 + 1);
            Integer toAccountNum = (int) (Math.random()*100 + 1);
            long amount = (long) (100000*Math.random());
//            executor.execute(bank);
            int finalIndex = i;
            synchronized (executor) {
                executor.submit(() ->
                {
                    System.out.println("" + finalIndex + ". ");
                    try {
                        bank.transfer(fromAccountNum, toAccountNum, amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();
        System.out.println("Final total balance: " +  bank.getTotalBalance(accounts));
    }

    public static synchronized void getHashMapOfAccounts(int numberOfAccounts) {
        for (int i = 1; i <= numberOfAccounts; i++){
            Integer accNumber = i;
            Account account = new Account (accNumber, (long) (100000*Math.random()),  true);
            accounts.put(accNumber, account);
        }
    }

    public static HashMap<Integer, Account> getAccounts() {
        return accounts;
    }
}
