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

    // Boolean method to check if account exists already
    private boolean AccountExists(Account account){
        return this.getAllAccounts().contains(account);
    }

    // Boolean method to check if account information is valid
    private boolean isAccountValid(Account account){
        return account.getPassword().length() <= 4 || account.getUsername() == "";
    }

    // Use AccountDAO to register a new user
    // @param Account
    // @return Account, null if account exists already
    public Account register(Account account){
        if (isAccountValid(account) || AccountExists(account)){
            return null;
        }

        return accountDAO.insertAccount(account);
    }

    // Use AccountDAO to validate if a user exists
    // @param Account
    // @return Account
    public Account login(Account account){
        System.out.println("Why no login?");
        if (this.AccountExists(account)){
            if (accountDAO.getAccountbyAccount(account) == null){
            }
            return accountDAO.getAccountbyAccount(account);
        }
        return null;
    }
}
