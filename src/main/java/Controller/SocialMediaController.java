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
        app.post("/login", this::loginHandler);

        /* POST METHODS  */ 

        // Endpoint for user registration
        app.post("/register", this::registerHandler);

        return app;
    }

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

    private void loginHandler(Context ctx){
        AccountService accountService = new AccountService();
        ObjectMapper om = new ObjectMapper();

        try {
            System.out.println("TESTTESTTEST");
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
}