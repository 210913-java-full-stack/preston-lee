package models;

public class Account
{
    private int account_id;
    private String account_type;
    private static double balance;

    public Account(int account_id, String account_type, double balance)
    {
        this.account_id = account_id;
        this.account_type = account_type;
        this.balance = balance;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public double getAccount_balance() {
        return balance;
    }

    public void setAccount_balance(double account_balance) {
        this.balance = account_balance;
    }
}