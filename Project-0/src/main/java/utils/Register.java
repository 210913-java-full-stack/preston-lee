package utils;

import DAO.UserDAO;
import Exceptions.UAE_TYPE;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Register{
    static String  user;
    static String  password;
    static String  first_name;
    static String  last_name;
    static Scanner sc = new Scanner(System.in);
    public static boolean RegisterUser(){
        boolean notFinished = true;
        //Asks for first name, last name, username, and password
        System.out.println("======REGISTER======\nEnter your first name:");
        first_name = sc.nextLine();
        //validation that the first name only contains letters and no foreign characters
        if(!isValidString(first_name))
        {
            return false;
        }
        System.out.println("Enter your last name:");
        last_name = sc.nextLine();
        //validation for the last name as well
        if(!isValidString(last_name))
        {
            return false;
        }
        System.out.println("Enter username:");
        user = sc.nextLine();
        System.out.println("Enter password:");
        password = sc.nextLine();
        User newUser = new User(user,password, first_name,last_name);

        //creates or grabs the existing connection
        try{
            Connection conn = ConnectionManager.getConnection();
            UserDAO dao = new UserDAO(conn);
            //sends new user info to newAccount
            dao.newAccount(newUser);
            System.out.println("Successfully made an account! Log in now.");
            //if account creation was successful
            return true;

        } catch (SQLException | UAE_TYPE e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isValidString(String string){
        //check if the user inputted a name, since it can't be null
        if(string == null || string.equals(""))
        {
            //tell the user what happened and return false
            System.out.println("First or last name cannot be blank.");
            return false;
        }
        //then check that each character is in the alphabet and there's no foreign characters
        for(int i = 0;i<string.length();i++)
        {
            char ch = string.charAt(i);
            //if there is a foreign character
            if((!(ch >= 'A' && ch <= 'Z')) && (!(ch >= 'a' && ch <= 'z')))
            {
                //tell the user what happened and return false
                System.out.println("Invalid characters in first or last name");
                return false;
            }
        }
        return true;
    }
}