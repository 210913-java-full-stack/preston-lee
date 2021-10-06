package DAO;

import MyArrayList.MyArrayList;
import models.Account;
import java.sql.SQLException;

public interface BankCRUD<A> {


    int getAccountId() throws SQLException;

    MyArrayList<Account> getAccountsByUser(String username) throws SQLException;

    void newBankAccount(String account_type, String username) throws SQLException;

    boolean depositFunds(int account_id, double deposit_amount) throws SQLException;

    boolean accountBelongsById(int account_id, String username) throws SQLException;

    boolean withdrawFunds(int account_id, double withdraw_amount) throws SQLException;

    boolean validFundsForWithdraw(int account_id, double withdraw_amount) throws SQLException;

    public boolean fundsBetweenAccounts(int account1, int account2, double amount) throws SQLException;
}






