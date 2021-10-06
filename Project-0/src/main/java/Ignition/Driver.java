package Ignition;

import utils.ConnectionManager;
import utils.MainMenu;
import java.sql.Connection;

/*This is where the fire is lit. CPU will start computing and cook up some hearty code. Will test connection and then start the
primary interface with the method below.
 */
public class Driver {
    public static void main(String[] args) {

        String fail = "Database not found";
        String pass = "\u001B[32mDatabase Connected";


       Connection conn = ConnectionManager.getConnection();

       //Test
       if (conn == null) {
           System.out.println(fail);
       }
    else {
           System.out.println(pass);
       }

        MainMenu.viewMenu();
    }


}






