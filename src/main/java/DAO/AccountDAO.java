package DAO;

// Initializing imports necessary for AccountDAO

// Account Model import
import Model.Account;
// ConnectionUtil import for database connection
import Util.ConnectionUtil;
// sql import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    // Get all Accounts
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}
