package Service;

// Import Model Message
import Model.Message;
// Import Message DAO
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    // Constructor of MessageService, initialize messageDAO object
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    // Get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // Boolean method checking if message is blank
    private Boolean isBlank(String message_text){
        return (message_text == "");
    }

    // Create new message
    public Message createMessage(Message message){
        // Check if message is blank
        if (isBlank(message.getMessage_text())){
            return null;
        }

        // Otherwise return message if successful, null if fail
        return messageDAO.insertMessage(message);
    }

    // Get message via message_id
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    // Delete message via message_id
    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    }

    // Boolean method to check if message_text is over 255
    private Boolean over255(String message_text){
        if (message_text.length() > 255){
            return true;
        }
        return false;
    }

    // Update message via message_id
    public Message updateMessageById(int message_id, String message_text){
        if (isBlank(message_text) || over255(message_text)){
            return null;
        }
        return messageDAO.updateMessageById(message_id, message_text);
    }
}
