package BankingSystem.Services;
import BankingSystem.Entities.Bank;
import BankingSystem.DAOClasses.BankDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class BankServices {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate3.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be thrown during initialization
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Obtaining a Session object
        Session session = getSessionFactory().openSession();

        // Creating a BankDAO object
        BankDAO bankDAO = new BankDAO(session);

        try {
            // Begin a transaction
            session.beginTransaction();

            // Your application logic here
            // For example, saving an entity using BankDAO
            Bank bank = new Bank();
            bank.setBankName("ABC bank ");
            bank.setCode("SB001");
            bank.setCity("Noida UP");
            bank.setAddress("Noida sector 15");
            bankDAO.save(bank);
            Bank existingBank = bankDAO.getBankByDetails(bank.getBankName(), bank.getCode(), bank.getCity(), bank.getAddress());
            if (existingBank == null) {
                bankDAO.save(bank);
                System.out.println("Bank details saved successfully.");
            } else {
                System.out.println("Bank details already exist in the database.");
            }

            // Commit the transaction
            session.getTransaction().commit();
            // Example: Get bank by ID
            int bankId = 113;
            Bank retrievedBank = bankDAO.getById(bankId);
            System.out.println("Select an action:");
            System.out.println("1. Retrieve bank details");
            System.out.println("2. Update bank details");
            System.out.println("3. Display all banks");
            System.out.println("4. Delete bank");
            System.out.print("Enter your choice: ");


            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Retrieve bank details
                    if(retrievedBank != null){
                        System.out.println("Retrieved Bank Details:");
                        System.out.println("Bank Name: " + retrievedBank.getBankName());
                        System.out.println("Bank Code: " + retrievedBank.getCode());
                        System.out.println("City: " + retrievedBank.getCity());
                        System.out.println("Address: " + retrievedBank.getAddress());}
                    else{
                        System.out.println("Failed to retrieve bank data ");
                    }
                    break;
                case 2:
                    retrievedBank.setCity("New City");
                    bankDAO.update(retrievedBank);
                    System.out.println("Bank details updated successfully.");
                    break;
                case 3:
                    // Display all banks
                    List<Bank> banks = bankDAO.getAll();
                    System.out.println("All Banks:");
                    for (Bank b : banks) {
                        System.out.println("Bank Name: " + b.getBankName() + ", Code: " + b.getCode());
                    }
                    break;
                case 4:
                    // Delete bank
                    bankDAO.delete(retrievedBank);
                    System.out.println("Bank deleted successfully.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            // Rollback the transaction if an exception occurs
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
            // Close the SessionFactory
            getSessionFactory().close();
        }
    }
}
