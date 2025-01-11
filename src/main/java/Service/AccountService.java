package Service;

// Importing Model Account
import Model.Account;
// Import Account DAO
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    // Initialize accountDAO
    private AccountDAO accountDAO;

    // Initialize no-args constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Get all Accounts via AccountDAO
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    // Use AccountDAO to register a new user
    // @param username, password
    // @return Account, null if account exists already
    public Account register(Account account){
        if (account.getPassword().length() <= 4 || account.getUsername() == "" || this.getAllAccounts().contains(account)){
            return null;
        }

        return null;
    }
}
