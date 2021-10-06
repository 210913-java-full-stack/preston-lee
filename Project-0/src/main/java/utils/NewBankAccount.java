package utils;

import DAO.BankDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class NewBankAccount {
    static String account_type;
    static int balance;
    static int newAccountID;
    static Scanner sc = new Scanner(System.in);

    public static boolean newBankAccount(String username)
    {
        //Print out the menu and collect information from the user
        System.out.println("======NEW ACCOUNT======\nEnter account type (Savings or Checking):");
        account_type = sc.nextLine();

        try {
            if (account_type.equals("Savings") || account_type.equals("Checking")) {
                //send information to make a new account. Each account is given a # automatically and a bal of 0
                Connection conn = ConnectionManager.getConnection();
                BankDAO bankDAO = new BankDAO(conn);
                bankDAO.newBankAccount(account_type, username);
                return true;
            } else {
                System.out.println("Must be a Checking or Savings account type.");
                LoggedInMenu.viewLoggedInMenu(username);
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
}