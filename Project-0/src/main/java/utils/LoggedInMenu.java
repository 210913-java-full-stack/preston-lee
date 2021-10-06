package utils;

import java.util.Scanner;

public class LoggedInMenu {


        public static void viewLoggedInMenu (String currentUser)
        {
            //once the user is logged in, we can run this menu getting the username of the current logged in customer
            Scanner sc = new Scanner(System.in);
            Boolean running = true;
            while (running) {
                System.out.println("======LOGGED IN======\nEnter Selection:\n1) Create new bank account.\n2) Deposit funds." +
                        "\n3) Withdraw funds.\n4) Transfer funds between accounts\n5) Look at all accounts\n6) Log out");
                String input = sc.nextLine();
                switch (input) {
                    case "1":
                        //Create a new bank account.
                        if (NewBankAccount.newBankAccount(currentUser)) {
                            System.out.println("Made new account!");
                        } else {
                            System.out.println("Unable to create account.");
                        }
                        break;
                    case "2":
                        //Deposit funds
                        DepositFunction.depositWithList(currentUser);
                        break;
                    case "3":
                        //Withdraw funds
                        WithdrawFunction.withdrawWithList(currentUser);
                        break;
                    case "4":
                        //Transfer funds between accounts
                        Transfer.transferFunds(currentUser);
                    case "5":
                        //Look at all accounts
                        Accountmenu.viewMenu(currentUser);
                        running = false;
                    case "6":
                        MainMenu.viewMenu();
                    default:
                        System.out.println("Invalid input! Please type one of the numbers from the list.");
                }
            }
        }
    }

