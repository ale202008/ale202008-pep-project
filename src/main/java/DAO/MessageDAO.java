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
}
