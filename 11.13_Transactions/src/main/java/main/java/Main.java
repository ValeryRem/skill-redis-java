package main.java;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    private static HashMap<Integer, Account> accounts = new HashMap<>();
    private static Bank bank = new Bank(accounts);
    private static int numberOfAccounts = 10;
    public static void main(String[] args) {
        getHashMapOfAccounts(numberOfAccounts);
        transferAll();
    }

    public static void transferAll() {
        int numberOfTransfers = 100;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        System.out.println("Initial hashmap:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + ": " + x.getValue().getBalance()));
        System.out.println("**************** Init total balance: " + bank.getTotalBalance(accounts));
        for (int i = 1; i <= numberOfTransfers; i++) {
            Integer fromAccountNum = (int) (Math.random() * 10 + 1);
            Integer toAccountNum = (int) (Math.random() * 10 + 1);
            long amount = (long) (100000 * Math.random());
            int finalI = i;
            executor.submit(() -> {
                try {
                    System.out.println("" + finalI + ". ");
                    bank.transfer(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final hashmap:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + ": " + x.getValue().getBalance()));
        System.out.println("************* Final total balance: " + bank.getTotalBalance(accounts));
    }

    public static synchronized void getHashMapOfAccounts(int numberOfAccounts) {
        for (int i = 1; i <= numberOfAccounts; i++) {
            Integer accNumber = i;
            Account account = new Account(accNumber, (long) (100000 * Math.random()), true);
            accounts.put(accNumber, account);
        }
    }

    public static HashMap<Integer, Account> getAccounts() {
        return accounts;
    }
}
