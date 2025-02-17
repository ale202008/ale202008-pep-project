package DAO;

// Account Model import
import Model.Message;
// ConnectionUtil import for database connection
import Util.ConnectionUtil;
// sql import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // Get all Messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Insert new Message
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            // Initialize SQL statement
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            // Initialize PreparedStatement object
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Set prepared statement object parameters
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            // Execute Insert
            ps.executeUpdate();
            // Grab updated ResultSet
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            // If true, meaning that new message was created, create message object to return
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(
                    generated_message_id,
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                );
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    // Get Message by id
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            // Initialize SQL statement
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            // Initialize prepared statement
            PreparedStatement ps = connection.prepareStatement(sql);
            // Implement prepared statement parameters
            ps.setInt(1, message_id);
            // Initiate query
            ResultSet rs = ps.executeQuery();
            // If there is a message in ResultSet, then return that message
            if (rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return message;
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Delete message via message_id
    public Message deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        System.out.println("WE GOT TO DAO DELETION");
        try {
            // Initialize SQL statement
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            // Initialize prepared statement
            PreparedStatement ps = connection.prepareStatement(sql);
            // Implement prepared statement parameters
            ps.setInt(1, message_id);
            // Initiate query
            ResultSet rs = ps.executeQuery();
            // Initialize return message object
            Message message = null;
            // If there is a message in ResultSet, then return that message
            if (rs.next()){
                message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
            // Rewrite for deletion sql string
            sql = "DELETE FROM Message WHERE message_id = ?";
            // Prepare new PreparedStatement with deletion sql string
            ps = connection.prepareStatement(sql);
            // Set paramater for prepared statement
            ps.setInt(1, message_id);
            // Execute update
            ps.executeUpdate();
            
            // return message, null if message did not exist
            return message;

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Update message via message_id
    public Message updateMessageById(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();

        try{
            // Initialize SQL statement
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            // Initialize prepared statement
            PreparedStatement ps = connection.prepareStatement(sql);
            // Implement prepared statement parameters
            ps.setString(1, message_text);
            ps.setInt(2, message_id);
            // Initiate update
            ps.executeUpdate();
            // Set SQL string to new select for updated message
            sql = "SELECT * FROM Message WHERE message_Id = ?";
            // Set PreparedStatement object to new SQL string
            ps = connection.prepareStatement(sql);
            // Set PreparedStatement object parameters
            ps.setInt(1, message_id);
            // Initiate query
            ResultSet rs = ps.executeQuery();
            // If message exists, return updated information
            if (rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    // Get all messages by a particular user given account_id
    public List<Message> getAllMessagesByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            // Initialize SQL string for all messages by account_id
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            // Initialize prepared statement with SQL string
            PreparedStatement ps = connection.prepareStatement(sql);
            // Set prepared statement parameters
            ps.setInt(1, account_id);
            // Run query
            ResultSet rs = ps.executeQuery();
            // Loop through ResultSet for messages, adding to array list
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
