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
        for (int i = 0; i < numberOfTransfers; i++) {
            Integer fromAccountNum = (int) (Math.random()*100 + 1);
            Integer toAccountNum = (int) (Math.random()*100 + 1);
            long amount = (long) (100000*Math.random());
//            executor.execute(bank);
            executor.submit(() ->
            {
                try {
                    bank.transfer(fromAccountNum, toAccountNum, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
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
