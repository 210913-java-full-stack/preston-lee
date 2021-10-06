package DAO;


import Exceptions.ACC_NULL;
import Exceptions.INC_PASS;
import Exceptions.UAE_TYPE;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


    public class UserDAO implements UserCRUD<User> {

        private Connection conn;
        public int   newAccount;

        public UserDAO(Connection conn)
        {
            this.conn = conn;
        }


        @Override
        public int getAccountId() throws SQLException {
            //We are grabbing the newest account ID that was used in the table.
            String sql = "SELECT * FROM user_accounts";
            PreparedStatement findAccNumStmt = conn.prepareStatement(sql);
            ResultSet rs = findAccNumStmt.executeQuery();

            while(rs.next())
            {
                newAccount = rs.getInt("account_id");
            }

            return newAccount;
        }

        @Override
        public void newAccount(User newUser) throws SQLException, UAE_TYPE {
            getAccountId();

            //have to check and make sure the user doesn't exist already.
            String sql = "SELECT username FROM user_info WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,newUser.getUsername());

            ResultSet rs = pstmt.executeQuery();

            //if results come back, the account already exists.
            if(rs.next())
            {
                throw new UAE_TYPE();
            }
            else
            {
                String insertStatement = "INSERT INTO user_accounts (username, account_id) VALUES (?,?)";
                PreparedStatement preparedInsertStmt = conn.prepareStatement(insertStatement);
                preparedInsertStmt.setString(1,newUser.getUsername());
                newAccount++;
                preparedInsertStmt.setInt(2, newAccount);
                preparedInsertStmt.executeUpdate();

                String nextInsertStmt = "INSERT INTO user_info (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedInsertStatement= conn.prepareStatement(nextInsertStmt);
                preparedInsertStatement.setString(1,newUser.getUsername());
                preparedInsertStatement.setString(2,newUser.getPassword());
                preparedInsertStatement.setString(3,newUser.getFirst_name());
                preparedInsertStatement.setString(4,newUser.getLast_name());
                preparedInsertStatement.executeUpdate();

                String acctInsertStmt = "INSERT INTO accounts (account_id,account_type, balance) VALUES (?,?,?)";
                PreparedStatement preparedAccountStmt = conn.prepareStatement(acctInsertStmt);
                preparedAccountStmt.setInt(1, newAccount);
                preparedAccountStmt.setString(2,"Checking");
                preparedAccountStmt.setInt(3,0);
                preparedAccountStmt.executeUpdate();
            }
        }

        @Override
        public User verifyLogin(String username, String password) throws SQLException, INC_PASS, ACC_NULL {
            User returnUser;
            //check username exists
            String getStatement = "SELECT * FROM user_info WHERE username = ?";
            PreparedStatement firstPreparedStmt = conn.prepareStatement(getStatement);
            firstPreparedStmt.setString(1,username);

            ResultSet checkUserResult = firstPreparedStmt.executeQuery();

            if(!checkUserResult.next())
            {
                throw new ACC_NULL();
            }

            //check username and password combination
            getStatement = "SELECT * FROM user_info WHERE (username = ?) AND (password = ?)";
            PreparedStatement preparedGetStmt = conn.prepareStatement(getStatement);
            preparedGetStmt.setString(1,username);
            preparedGetStmt.setString(2,password);

            ResultSet getUserResult = preparedGetStmt.executeQuery();

            //if this username and password combination exist, then log them in. Otherwise they put in the wrong password, throw the exception.
            if(getUserResult.next())
            {
                returnUser = new User(getUserResult.getString("username"), getUserResult.getString("password"), getUserResult.getString("first_name"), getUserResult.getString("last_name"));
                return returnUser;
            }
            else
            {
                throw new INC_PASS();
            }
        }
    }