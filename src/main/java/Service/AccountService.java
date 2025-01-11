package Service;

// Importing Model Account
import Model.Account;
// Import Account DAO
import DAO.AccountDAO;


public class AccountService {
    // Initialize accountDAO
    private AccountDAO accountDAO;

    // Initialize no-args constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Use AccountDAO to register a new user
    // @param username, password
    // @return Account, null if account exists already
    public Account register(String name, String password){
        return null;
    }
}
