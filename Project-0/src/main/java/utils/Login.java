package utils;

import DAO.UserDAO;
import Exceptions.ACC_NULL;
import Exceptions.INC_PASS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    static String user;
    static String password;
    static Scanner sc = new Scanner(System.in);
    //
    public static String LoginUser()
    {
        //printing log in screen and getting information from user
        System.out.println("======LOG IN======\nEnter username:");
        user = sc.nextLine();
        System.out.println("Enter password:");
        password = sc.nextLine();
        try{
            //validate the login information in the UserDao instance 'dao'
            Connection conn = ConnectionManager.getConnection();
            UserDAO dao = new UserDAO(conn);

            if(dao.verifyLogin(user,password) != null)
            {
                //if it returns a User, it succeeded, and now we can return the username so we know who is logged in.
                return user;
            }

        } catch (SQLException | INC_PASS | ACC_NULL e) {
            System.out.println(e.getMessage());
            MainMenu.viewMenu();
        }
        return null;
    }
}
