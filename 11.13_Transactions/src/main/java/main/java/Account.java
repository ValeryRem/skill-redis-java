package main.java;

public class Account
{
    private long balance;
    private Integer accNumber;
    private boolean open;

    public Account(Integer accNumber, long balance, boolean open) {
        this.accNumber = accNumber;
        this.balance = balance;
        this.open = open;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Integer getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Integer accNumber) {
        this.accNumber = accNumber;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
