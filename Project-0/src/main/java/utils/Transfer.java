package utils;

import DAO.BankDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Transfer {
    static double transfer_amount;
    static int account1;
    static int account2;

    public static void transferFunds(String username)
    {
        //print account list so user can see their accounts
        PrintAccountList.printAccountList(username);

        Scanner sc = new Scanner(System.in);
        //get relevant information from the user
        System.out.println("======TRANSFER FUNDS======");
        System.out.println("Enter the source account number: ");
        account1 = Integer.parseInt(sc.nextLine());
        System.out.println("Enter the destination account number: ");
        account2 = Integer.parseInt(sc.nextLine());

        try{
            Connection conn = ConnectionManager.getConnection();
            //create dao instance to perform deposit
            BankDAO dao = new BankDAO(conn);

            if(dao.accountBelongsById(account1,username) && dao.accountBelongsById(account2,username))
            {
                //if the account belongs to the user then ask how much they'd like to deposit
                System.out.println("Enter the amount to transfer: ");
                transfer_amount = Double.parseDouble(sc.nextLine());
                if(transfer_amount < 1)
                {
                    System.out.println("Transfer can't be less than $1. Please try again.");
                    transferFunds(username);
                }
            }
            else
            {
                //if accountBelongsById fails it means the user entered an account # that isn't theirs
                //I send them back up to the top of the deposit function to try again.
                transferFunds(username);
            }

            if(dao.fundsBetweenAccounts(account1,account2,transfer_amount))
            {
                //By this point we've checked the account number and gotten the amount.
                //We get into here by successfully depositing money into an account
                System.out.println(transfer_amount + " deposited into account number " + account2 + " from account number " + account1);
                LoggedInMenu.viewLoggedInMenu(username);
            }
            else
            {
                transferFunds(username);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

