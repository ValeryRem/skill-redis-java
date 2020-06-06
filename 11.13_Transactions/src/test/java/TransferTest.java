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
    Bank bank;// = new Bank(accounts);

    @Before
    public void setUp() {
        Main.getHashMapOfAccounts(100);
        accounts =  Main.getAccounts();
        bank = new Bank(accounts);
    }
    @Test
    public void testBalanceOfTransfers() {
        System.out.println("Bank accounts and final balance:");
        accounts.entrySet().forEach(x -> System.out.println(x.getKey() + " - " + x.getValue().getBalance()));
        long initBalance = bank.getTotalBalance(accounts);
        try {
//            Main.transferAll();
            for (int i = 1; i < 10; i++) {
                int fromAccountNum = (int) (Math.random() * 100 + 1);
                int toAccountNum = (int) (Math.random() * 100 + 1);
                long amount = (long) (100000 * Math.random());
                bank.transfer(fromAccountNum, toAccountNum, amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finBalance = bank.getTotalBalance(accounts);
        assertEquals(initBalance, finBalance, DELTA);
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
