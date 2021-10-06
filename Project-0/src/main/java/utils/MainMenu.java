package utils;

import java.util.Scanner;

public class MainMenu  {

    public static void viewMenu() {
        //Initializes scanner for system input
        Scanner sc = new Scanner(System.in);
        //Putting our username of choice in the String Pool
        String currentUser = "";

        boolean running = true;
        while (running) {
            System.out.println("======MAIN MENU======\nEnter Selection:\n\n1) Register for an account.\n2) Log in to your account.\nQ) Quit");
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    //Register the user.
                    if (Register.RegisterUser()) {

                    } else {
                        System.out.println("Account creation unsuccessful");
                        Register.RegisterUser();
                    }
                case "2":
                    currentUser = Login.LoginUser();
                    if (currentUser != null) {
                        System.out.println("Login Complete " + currentUser);
                        //log in was successful
                        LoggedInMenu.viewLoggedInMenu(currentUser);
                    } else {
                        System.out.println("Login failed.");
                        Login.LoginUser();
                    }
                case "Q":
                case "q":
                    System.exit(0);
                default:
                    System.out.println("Input not recognized");
                    continue;
            }
        }
    }
}