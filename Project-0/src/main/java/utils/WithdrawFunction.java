package utils;

import DAO.BankDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class WithdrawFunction {

    static double withdraw_amount;
    static int account_number;

    public static void withdrawWithList(String username)
    {
        //print account list
        PrintAccountList.printAccountList(username);

        Scanner sc = new Scanner(System.in);
        //get relevant information from the user
        System.out.println("======WITHDRAW FUNDS======");
        System.out.println("Enter the account number you want to withdraw from: ");
        account_number = Integer.parseInt(sc.nextLine());
        Connection conn = ConnectionManager.getConnection();
        //create dao instance to perform withdraw
        BankDAO dao = new BankDAO(conn);

        //if the account belongs to the user
        try {
            if(dao.accountBelongsById(account_number,username))
            {
                //get the amount to withdraw
                System.out.println("Enter the amount to withdraw: ");
                withdraw_amount = Double.parseDouble(sc.nextLine());
            }
            else
            {
                //if the account doesn't belong to them, send them back to try again
                withdrawWithList(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //okay, send in withdraw amount
        try {
            if(dao.withdrawFunds(account_number,withdraw_amount))
            {
                //successfully withdrawn, let the user know via a printout
                System.out.println(withdraw_amount + " withdrawn from account number " + account_number);
                //then list accounts in case we want to do more
                Accountmenu.viewMenu(username);
            }
            else
            {
                withdrawWithList(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
