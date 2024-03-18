package BankingSystem.Services;

import BankingSystem.Entities.Account;
import BankingSystem.Entities.Customer;
import BankingSystem.DAOClasses.AccountDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;



public class AccountServices {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        Scanner scanner = new Scanner(System.in);


        try {
            SessionFactory sessionFactory = new Configuration().configure("hibernate3.cfg.xml").buildSessionFactory();
            // Open a new session
            session = sessionFactory.openSession();
            // Begin a new transaction
            transaction = session.beginTransaction();
            AccountDAO accountDAO  = new AccountDAO(session);

            int choice;
            do {
                System.out.println("Account Service Menu:");
                System.out.println("1. Add Account");
                System.out.println("2. Update Account");
                System.out.println("3. Delete Account");
                System.out.println("4. View All Accounts");
                System.out.println("5. Search Account by ID");
                System.out.println("6. Withdraw from Account");
                System.out.println("7. Deposit into Account");
                System.out.println("8. Check Account Balance");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addAccount(session, scanner);
                        break;
                    case 2:
                        updateAccount(session, scanner);
                        break;
                    case 3:
                        deleteAccount(session, scanner);
                        break;
                    case 4:
                        viewAllAccounts(session);
                        break;
                    case 5:
                        searchAccountById(session, scanner);
                        break;
                    case 6:
                        withdrawtoaccount(session,scanner);
                        break;
                    case 7:
                        depositIntoAccount(session, scanner);
                        break;
                    case 8:
                        checkAccountBalance(session, scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 0);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

    // Method to add a new account
    // Method to add a new account
    private static void addAccount(Session session, Scanner scanner) {
        System.out.println("Enter account details:");
        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Retrieve the customer associated with the provided customer ID
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            // Create a new account object
            Account account = new Account();
            account.setCustomer(customer); // Set the retrieved customer
            account.setBalance(balance);

            // Save the account object to the database
            session.save(account);
            System.out.println("Account added successfully.");
        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }


    // Method to update an existing account
    // Method to update an existing account
    private static void updateAccount(Session session, Scanner scanner) {
        System.out.print("Enter Account ID to update: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the account by ID
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            System.out.println("Enter new details:");
            System.out.print("Balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Update account details
            account.setBalance(balance);

            // Save the updated account to the database
            session.update(account);
            System.out.println("Account updated successfully.");
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }


    // Method to delete an existing account
    private static void deleteAccount(Session session, Scanner scanner) {
        System.out.print("Enter Account ID to delete: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the account by ID
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            // Delete the account from the database
            session.delete(account);
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }

    // Method to view all accounts
    // Method to view all accounts
    private static void viewAllAccounts(Session session) {
        // Retrieve all accounts from the database
        List<Account> accounts = session.createQuery("FROM Account", Account.class).getResultList();

        // Display all accounts
        System.out.println("All Accounts:");
        for (Account account : accounts) {
            System.out.println("ID: " + account.getCustomer().getId());
            System.out.println("Customer ID: " + account.getCustomer().getId()); // Assuming getId() returns the customer ID
            System.out.println("Customer Name: " + account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName()); // Assuming getFirstName() and getLastName() are methods in the Customer class
            System.out.println("Balance: " + account.getBalance());
            System.out.println();
        }
    }


    // Method to search for an account by account number
      // Display account details
    // Method to search for an account by account number
    private static void searchAccountById(Session session, Scanner scanner) {
        System.out.print("Enter Account ID to search: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the account by ID
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            // Display account details
            System.out.println("Account Details:");
            System.out.println("ID: " + account.getCustomer().getId());
            System.out.println("Customer ID: " + account.getCustomer().getId()); // Assuming getId() returns the customer ID
            System.out.println("Customer Name: " + account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName()); // Assuming getFirstName() and getLastName() are methods in the Customer class
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }




    // Method to deposit into an account
    private static void depositIntoAccount(Session session, Scanner scanner) {
        System.out.print("Enter Account ID to deposit into: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the account by ID
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Perform deposit
            account.deposit(amount);
            session.update(account); // Update account in database
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }

    // Method to check account balance
    private static void checkAccountBalance(Session session, Scanner scanner) {
        System.out.print("Enter Account ID to check balance: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the account by ID
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            // Display account balance
            System.out.println("Account Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }
    public static void withdrawtoaccount(Session session, Scanner scanner) {
        System.out.println("Enter account number:");
        int accountNumber = scanner.nextInt();
        Account account = session.get(Account.class, accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Enter withdrawal amount:");
        double amount = scanner.nextDouble();

        double balance = account.getBalance();
        Account.AccountType accountType = account.getAccountType();

        // Check if the account type is savings and if the balance after withdrawal will be at least 1000
        if (accountType == Account.AccountType.SAVINGS && balance - amount < 1000) {
            System.out.println("Withdrawal failed. Savings account must maintain a minimum balance of 1000.");
            return;
        }

        // Perform the withdrawal
        if (balance >= amount) {
            balance -= amount;
            account.setBalance(balance);
            System.out.println("Amount " + amount + " withdrawn. Current balance: " + balance);
        } else {
            System.out.println("Withdrawal failed. Insufficient funds.");
        }
    }




}
