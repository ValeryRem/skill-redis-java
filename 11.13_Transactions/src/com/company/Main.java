package com.company;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
        int numberOfTransfers = 100;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        Bank.getHashMapOfAccounts();
        System.out.println("Total balance before transfers: " + Bank.getTotalBalance() + " RUR.");
        for (int i = 0; i < numberOfTransfers; i++) {
            Integer fromAccountNum = (int) (Math.random()*1000 + 1);
            Integer toAccountNum = (int) (Math.random()*1000 + 1);
            long amount = (long) (100000*Math.random());
            Bank bank = new Bank(fromAccountNum, toAccountNum, amount);
            executor.execute(bank);
//            executor.submit(() ->
//            {
//                try {
//                    bank.transfer(fromAccountNum, toAccountNum, amount);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
        }
        executor.shutdown();
        System.out.println("Total balance after transfers: " + Bank.getTotalBalance() + " RUR.");
    }
}
