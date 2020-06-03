import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static HashMap<Integer, Account> accounts;
    private final static int numberOfAccounts = 1000;

    public static void main(String[] args) {
        int numberOfTransfers = 100;
        accounts = getHashMapOfAccounts();
        System.out.println("accounts HashMap size: " + accounts.size());
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        for (int i = 0; i < numberOfTransfers; i++) {
            Integer fromAccountNum = (int) (Math.random()*1000 + 1);
            Integer toAccountNum = (int) (Math.random()*1000 + 1);
            long amount = (long) (100000*Math.random());
            Bank bank = new Bank(accounts, fromAccountNum, toAccountNum, amount);
            executor.execute(bank);
        }
        executor.shutdown();
    }

    public static synchronized HashMap<Integer, Account> getHashMapOfAccounts() {
        accounts = new HashMap<>();
        for (int i = 1; i <= numberOfAccounts; i++){
            Integer accNumber = i;
            Account account = new Account (accNumber, (long) (100000*Math.random()),  true);
            accounts.put(accNumber, account);
        }
        return accounts;
    }
}
