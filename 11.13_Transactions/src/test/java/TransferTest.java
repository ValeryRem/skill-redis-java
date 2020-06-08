import main.java.Account;
import main.java.Main;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import main.java.Bank;

public class TransferTest {
    private static final double DELTA = 0.00001;
    private HashMap<Integer, Account> accounts;
    private Bank bank;
    private long initBalance;
    private ThreadPoolExecutor executor;
    @Before
    public void setUp() {
        Main.getHashMapOfAccounts(10);
        accounts =  Main.getAccounts();
        bank = new Bank(accounts);
        initBalance = bank.getTotalBalance(accounts);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }

    @Test
    public void testBalanceOfTransfers() {
        System.out.println("Bank accounts and final balance:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + " - " + x.getValue().getBalance()));
        toDoAllTransfers(100);
        long finBalance = bank.getTotalBalance(accounts);
        assertEquals(initBalance, finBalance, DELTA);
    }

    @Test
    public void testRecursiveTransfer() {
        try {
            bank.transfer(1, 1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Recursive transfer is impossible!", bank.getOutput());
    }

    @Test
    public void testPositiveCurrentBalance (){
        toDoAllTransfers(100);
        assertTrue(bank.isPositiveCurrentBalanceIndicator());
    }

    @Test
    public void testThreadPoolExecution () {
        toDoAllTransfers(100);
        assertTrue(Thread.currentThread().isAlive());
//        assertTrue(executor.isShutdown());
    }

    private void toDoAllTransfers(int numberOfTransfers) {
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
//        try {
//            for (int i = 1; i < 100; i++) {
//                int fromAccountNum = (int) (Math.random() * 10 + 1);
//                int toAccountNum = (int) (Math.random() * 10 + 1);
//                long amount = (long) (100000 * Math.random());
//                bank.transfer(fromAccountNum, toAccountNum, amount);
//            }
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
