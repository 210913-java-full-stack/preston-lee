package DAO;


import MyArrayList.MyArrayList;
import models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAO implements BankCRUD<Account> {


    private Connection conn;
    public int newestAccountId;

    public BankDAO(Connection conn)
    {
        this.conn = conn;
    }


    @Override
    public int getAccountId() throws SQLException {
        //We are grabbing the newest account ID that was used by the auto increment in the table.
        String sql = "SELECT * FROM user_accounts";
        PreparedStatement findAccNumStmt = conn.prepareStatement(sql);
        ResultSet rs = findAccNumStmt.executeQuery();

        while(rs.next())
        {
            newestAccountId = rs.getInt("account_id");
        }

        return newestAccountId;
    }

    @Override
    public MyArrayList<Account> getAccountsByUser(String username) throws SQLException {
        //We are getting all of the bank accounts belonging to whoever is logged in, passed in by username.
        String getAccountsSql = "SELECT * FROM accounts a JOIN user_accounts ua ON a.account_id = ua.account_id WHERE username = ?";
        PreparedStatement getAccountsStmt = conn.prepareStatement(getAccountsSql);
        getAccountsStmt.setString(1,username);
        ResultSet rs = getAccountsStmt.executeQuery();

        MyArrayList<Account> accountList = new MyArrayList<Account>();

        while(rs.next())
        {
            Account newAccount = new Account(rs.getInt("account_id"),rs.getString("account_type"),rs.getDouble("balance"));
            accountList.add(newAccount);
        }

        return accountList;
    }

    @Override
    public void newBankAccount(String account_type, String username) throws SQLException {
        //creating new bank account. get most recently used ID
        getAccountId();

        String insertStatement = "INSERT INTO user_accounts (username, account_id) VALUES (?,?)";
        PreparedStatement preparedInsertStmt = conn.prepareStatement(insertStatement);
        preparedInsertStmt.setString(1,username);
        //taking the most recently used account number and increasing it by one gives the newest unused number
        newestAccountId++;
        preparedInsertStmt.setInt(2,newestAccountId);
        preparedInsertStmt.executeUpdate();

        String acctInsertStmt = "INSERT INTO accounts (account_id,account_type, balance) VALUES (?,?,?)";
        PreparedStatement preparedAccountStmt = conn.prepareStatement(acctInsertStmt);
        preparedAccountStmt.setInt(1,newestAccountId);
        preparedAccountStmt.setString(2,account_type);
        preparedAccountStmt.setInt(3,0);
        preparedAccountStmt.executeUpdate();

    }

    @Override
    public boolean depositFunds(int account_id, double deposit_amount) throws SQLException {
        //Now deposit whatever amount into that account
        String depositSql = "UPDATE accounts SET balance = (balance + ?) WHERE account_id = ?";
        PreparedStatement depositStmt = conn.prepareStatement(depositSql);
        depositStmt.setDouble(1,deposit_amount);
        depositStmt.setInt(2,account_id);
        depositStmt.executeUpdate();

        return true;
    }

    @Override
    public boolean accountBelongsById(int account_id, String username) throws SQLException {
        //double check that the account belongs to whoever is logged in for both deposit and withdrawal
        String accountCheck = "SELECT * FROM user_accounts WHERE (username = ?) AND (account_id = ?)";
        PreparedStatement preparedCheckStmt = conn.prepareStatement(accountCheck);
        preparedCheckStmt.setString(1,username);
        preparedCheckStmt.setInt(2,account_id);
        ResultSet rs = preparedCheckStmt.executeQuery();

        if(!rs.next())
        {
            System.out.println("Inputted account number not associated with your account. Please try again.");
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean withdrawFunds(int account_id, double withdraw_amount) throws SQLException {
        //make sure we have enough funds
        if(!validFundsForWithdraw(account_id,withdraw_amount))
        {
            System.out.println("Not enough funds. Withdraw unsuccessful.");
            return false;
        }
        //if we have enough funds then update the amount minus however much we withdrew
        String withdrawSQL = "UPDATE accounts SET balance = (balance - ?) WHERE account_id = ?";
        PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSQL);
        withdrawStmt.setDouble(1,withdraw_amount);
        withdrawStmt.setInt(2,account_id);
        withdrawStmt.executeUpdate();
        return true;
    }

    @Override
    public boolean validFundsForWithdraw(int account_id, double withdraw_amount) throws SQLException {
        //getting balance from the account we're trying to withdraw from
        String validateSQL = "SELECT balance FROM accounts WHERE account_id = ?";
        PreparedStatement validateStmt = conn.prepareStatement(validateSQL);
        validateStmt.setInt(1,account_id);
        ResultSet rs = validateStmt.executeQuery();
        while(rs.next())
        {
            //get current balance
            double currentBalance = rs.getDouble("balance");
            if(currentBalance >= withdraw_amount)
            {
                //if it's above or equal to the withdraw amount then withdrawal is go
                return true;
            }
            else
            {
                //if it isn't then we can't perform the withdraw
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean fundsBetweenAccounts(int account1, int account2, double amount) throws SQLException {
        //account1 is the giver
        //account2 is the receiver
        //checks if account1 has funds
        if(!validFundsForWithdraw(account1,amount))
        {
            System.out.println("Not enough funds. Transfer unsuccessful.");
            return false;
        }
        //account1 has enough funds. Take the amount out of account1 first
        String withdrawSQL = "UPDATE accounts SET balance = (balance - ?) WHERE account_id = ?";
        PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSQL);
        withdrawStmt.setDouble(1,amount);
        withdrawStmt.setInt(2,account1);
        withdrawStmt.executeUpdate();

        //now add that amount to account2
        String depositSQL = "UPDATE accounts SET balance = (balance + ?) WHERE account_id = ?";
        PreparedStatement depositStmt = conn.prepareStatement(depositSQL);
        depositStmt.setDouble(1,amount);
        depositStmt.setInt(2, account2);
        depositStmt.executeUpdate();

        return true;
    }


}
