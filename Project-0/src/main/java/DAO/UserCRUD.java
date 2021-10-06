package DAO;

import Exceptions.ACC_NULL;
import Exceptions.INC_PASS;
import Exceptions.UAE_TYPE;
import models.User;

import java.sql.SQLException;

public interface UserCRUD <T> {

        //get current BankAccount ID
        public int getAccountId() throws SQLException;

        //create user account
        //save new user info to database
        public void newAccount(User newUser) throws SQLException, UAE_TYPE;

        //read user info from database to login
        public T verifyLogin(String username, String password) throws SQLException, INC_PASS, ACC_NULL;
    }
