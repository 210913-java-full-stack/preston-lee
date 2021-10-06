package utils;

import DAO.BankDAO;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DepositFunction {


    static double deposit_amount;
    static int account_number;

    public static void depositWithList(String username)
    {
        PrintAccountList.printAccountList(username);
        Scanner sc = new Scanner(System.in);
        //get relevant information from the user
        System.out.println("======DEPOSIT FUNDS======");
        System.out.println("Enter the account number you want to deposit into: ");
        account_number = Integer.parseInt(sc.nextLine());
        try{
            Connection conn = ConnectionManager.getConnection();
            //create dao instance to perform deposit
            BankDAO dao = new BankDAO(conn);

            if(dao.accountBelongsById(account_number,username))
            {
                //if the account belongs to the user then ask how much they'd like to deposit
                System.out.println("Enter the amount to deposit: ");
                deposit_amount = Double.parseDouble(sc.nextLine());
                if(deposit_amount < 1)
                {
                    System.out.println("Deposit can't be less than $1. Please try again.");
                    depositWithList(username);
                }
            }
            else
            {
                //if accountBelongsById fails it means the user entered an account # that isn't theirs
                //I send them back up to the top of the deposit function to try again.
                depositWithList(username);
            }

            if(dao.depositFunds(account_number,deposit_amount))
            {
                //By this point we've checked the account number and gotten the amount.
                //We get into here by successfully depositing money into an account
                System.out.printf("$%.2f deposited into account number " + account_number + "\n", deposit_amount);
                Accountmenu.viewMenu(username);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}