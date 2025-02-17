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
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    // Get an Account by a Account
    public Account getAccountbyAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            Account res = new Account();
            if (rs.next()){
                res = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            return res;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Insert a new Account
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL statemt to insert new Account record
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            // Initialize preparedStatement object with sql string
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Set first ? to account username
            ps.setString(1, account.getUsername());
            // Set second ? to account password
            ps.setString(2, account.getPassword());
            // Execute insert
            ps.executeUpdate();
            // Grab updated ResultSet
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    
}
