package utils;

import java.util.Scanner;

public class Accountmenu {


        static Scanner sc = new Scanner(System.in);

        public static void viewMenu(String username) {
            //invoke the printout
            PrintAccountList.printAccountList(username);
            //after showing them their accounts, printout asking them if they want to deposit or withdraw
            System.out.println("What would you like to do?\n1) Deposit Funds\n2) Withdraw funds\nQ) Quit");
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    DepositFunction.depositWithList(username);
                    break;
                case "2":
                    WithdrawFunction.withdrawWithList(username);
                    break;
                case "Q":
                case "q":
                    LoggedInMenu.viewLoggedInMenu(username);
            }
        }
    }


