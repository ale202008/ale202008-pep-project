package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

// Import Model java files
import Model.Account;
import Model.Message;

// Importing Service java files for Account and Message
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        /* GET METHODS */

        // Endpoint to get all messages
        app.get("/messages", this::getAllMessagesHandler);
        // Endpoint to retrieve a message via id
        app.get("/messages/{message_id}", this::getMessageById);

        /* POST METHODS  */ 

        // Endpoint for user registration
        app.post("/register", this::registerHandler);
        // Endpoint for login
        app.post("/login", this::loginHandler);
        // Endpoint to create message
        app.post("/messages", this::messageHandler);

        /* DELETE METHODS */

        // Endpoint to delete message via id
        app.delete("/messages/{message_id}", this::deleteMessageById);

        /* PATCH METHODS */

        // Endpoint to update message
        app.patch("/messages/{message_id}", this::updateMessageById);

        return app;
    }

    // Handler for register endpoint
    private void registerHandler(Context ctx) {
        AccountService accountService = new AccountService();
        ObjectMapper om = new ObjectMapper();

        try {
            String jsonString = ctx.body();
            Account account = om.readValue(jsonString, Account.class);
            Account res = accountService.register(account);
            if (res != null){
                ctx.json(res).status(200);
            }
            else {
                ctx.status(400);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Handler for login
    private void loginHandler(Context ctx){
        AccountService accountService = new AccountService();
        ObjectMapper om = new ObjectMapper();

        try {
            String jsonString = ctx.body();
            Account account = om.readValue(jsonString, Account.class);
            Account res = accountService.login(account);
            if (res != null){
                ctx.json(res).status(200);
            }
            else {
                ctx.status(401);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Handler for message creation
    private void messageHandler(Context ctx){
        MessageService messageService = new MessageService();
        ObjectMapper om = new ObjectMapper();

        
        try {
            String jsonString = ctx.body();
            Message message = om.readValue(jsonString, Message.class);
            Message res = messageService.createMessage(message);
            if (res != null){
                ctx.json(res).status(200);
            }
            else{
                ctx.status(400);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Handler for get all messages
    private void getAllMessagesHandler(Context ctx){
        MessageService messageService = new MessageService();
        try{
            ctx.json(messageService.getAllMessages());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Handler for get message by message_id
    private void getMessageById(Context ctx){
        MessageService messageService = new MessageService();

        try{
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
            if (message != null){
                ctx.json(message).status(200);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    // Handler to delete message via message_id
    private void deleteMessageById(Context ctx){
        MessageService messageService = new MessageService();
        
        try{
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.deleteMessageById(messageId);
            if (message != null){
                ctx.json(message).status(200);
            }
            else{
                ctx.status(200);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    // Handler for update message via message_id
    private void updateMessageById(Context ctx){
        MessageService messageService = new MessageService();
        ObjectMapper om = new ObjectMapper();
        
        try{
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            String jsonString = ctx.body();
            Message message = om.readValue(jsonString, Message.class);
            String updated_text = message.getMessage_text();
            message = messageService.updateMessageById(message_id, updated_text);
            if (message != null){
                ctx.json(message).status(200);
            }
            else{
                ctx.status(400);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    
}