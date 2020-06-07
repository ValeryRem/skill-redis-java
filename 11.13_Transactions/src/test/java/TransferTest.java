import main.java.Account;
import main.java.Main;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;
import main.java.Bank;

public class TransferTest {
    private static final double DELTA = 0.00001;
    private HashMap<Integer, Account> accounts;
    private Bank bank;
    private long initBalance;

    @Before
    public void setUp() {
        Main.getHashMapOfAccounts(10);
        accounts =  Main.getAccounts();
        bank = new Bank(accounts);
        initBalance = bank.getTotalBalance(accounts);
    }
    @Test
    public void testBalanceOfTransfers() {
        System.out.println("Bank accounts and final balance:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + " - " + x.getValue().getBalance()));
        try {
            for (int i = 1; i < 100; i++) {
                int fromAccountNum = (int) (Math.random() * 10 + 1);
                int toAccountNum = (int) (Math.random() * 10 + 1);
                long amount = (long) (100000 * Math.random());
                bank.transfer(fromAccountNum, toAccountNum, amount);
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finBalance = bank.getTotalBalance(accounts);
        assertEquals(initBalance, finBalance, DELTA);
        if (initBalance == finBalance) {
            System.out.println("Test of balance of transfers = OK");
        } else {
            System.out.println("Test of balance of transfers = collapsed!");
        }
    }

    @Test
    public void testRecursiveTransfer() {
        try {
            bank.transfer(1, 1, 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Recursive transfer is impossible!", bank.getOutput());
    }
}
