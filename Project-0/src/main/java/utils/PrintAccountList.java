package utils;

import DAO.BankDAO;
import MyArrayList.MyArrayList;
import models.Account;

import java.sql.Connection;
import java.sql.SQLException;

    public class PrintAccountList {
        public static void printAccountList(String username)
        {
            System.out.println("======ACCOUNT LIST======");

            try{
                //We are getting the accounts in an arraylist in order to print out for the user.
                Connection conn = ConnectionManager.getConnection();
                BankDAO dao = new BankDAO(conn);
                MyArrayList<Account> accountList;
                accountList = dao.getAccountsByUser(username);

                for(int i = 0; i<accountList.size();i++)
                {
                    //call printmylist to format it.
                    PrintList.printMyList(accountList.get(i));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("========================");
        }
    }

