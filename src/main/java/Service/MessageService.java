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
    private Boolean isBlank(Message message){
        return (message.getMessage_text() == "");
    }

    // Create new message
    public Message createMessage(Message message){
        // Check if message is blank
        if (isBlank(message)){
            return null;
        }

        // Otherwise return message if successful, null if fail
        return messageDAO.insertMessage(message);
    }

    // Get message by message_id
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }
}
