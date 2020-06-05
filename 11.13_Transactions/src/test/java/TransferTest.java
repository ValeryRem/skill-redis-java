import main.java.Account;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;
import main.java.Bank;



public class TransferTest {
    private static final double DELTA = 0.00001;
    private final static int numberOfAccounts = 10;
    private HashMap<Integer, Account> accounts;
    private int fromAccountNum;
    private int toAccountNum;
    private long amount;

    @Before
    public void setUp() {
       Bank.getHashMapOfAccounts(numberOfAccounts);
    }
    @Test
    public void testBalanceOfTransfers() {
        long initBalance = Bank.getTotalBalance();
        try {
            for (int i = 1; i < 100; i++) {
                fromAccountNum = (int) (Math.random()*10 + 1);
                toAccountNum = (int) (Math.random()*10 + 1);
                amount = (long) (100000*Math.random());
                Bank.transfer(fromAccountNum, toAccountNum, amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finBalance = Bank.getTotalBalance();
        System.out.println("Bank accounts and final balance:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + " - " + x.getValue().getBalance()));
        assertEquals(initBalance, finBalance, DELTA);
    }

    @Test
    public void testRecursiveTransfer() {
        try {
            Bank.transfer(1, 1, 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Recursive transfer is impossible!", Bank.getOutput());
    }

}
