package BankingSystem.Services;
import BankingSystem.Entities.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomerService {

    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory
        SessionFactory sessionFactory = new Configuration().configure("hibernate3.cfg.xml").buildSessionFactory();

        // Initialize variables
        Scanner scanner = new Scanner(System.in);
        Session session = null;
        Transaction transaction = null;

        try {
            // Open a new session
            session = sessionFactory.openSession();
            // Begin a new transaction
            transaction = session.beginTransaction();

            // Prompt user for operation choice
            System.out.println("Customer Service Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. View All Customers");
            System.out.println("5. Search Customer by ID");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Perform operation based on user choice
            switch (choice) {
                case 1:
                    addCustomer(session, scanner);
                    break;
                case 2:
                    updateCustomer(session, scanner);
                    break;
                case 3:
                    deleteCustomer(session, scanner);
                    break;
                case 4:
                    viewAllCustomers(session);
                    break;
                case 5:
                    searchCustomerById(session, scanner);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            // Rollback the transaction if an exception occurs
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the session and the session factory
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }

    // Method to add a new customer
    private static void addCustomer(Session session, Scanner scanner) {
        System.out.println("Enter customer details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Mobile Numbers (comma-separated): ");
        String mobileNumbersStr = scanner.nextLine();
        List<String> mobileNumbers = Arrays.asList(mobileNumbersStr.split(","));

        // Create a new customer object
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCustomerId(customerId);
        customer.setAddress(address);
        customer.setMobileNumbers(mobileNumbers);

        // Save the customer object to the database
        session.save(customer);
        System.out.println("Customer added successfully.");
    }

    // Method to update an existing customer
    private static void updateCustomer(Session session, Scanner scanner) {
        System.out.print("Enter Customer ID to update: ");
        String customerId = scanner.nextLine();

        // Retrieve the customer by ID
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            System.out.println("Enter new details:");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Mobile Numbers (comma-separated): ");
            String mobileNumbersStr = scanner.nextLine();
            List<String> mobileNumbers = Arrays.asList(mobileNumbersStr.split(","));

            // Update customer details
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setAddress(address);
            customer.setMobileNumbers(mobileNumbers);

            // Save the updated customer to the database
            session.update(customer);
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }

    // Method to delete an existing customer
    private static void deleteCustomer(Session session, Scanner scanner) {
        System.out.print("Enter Customer ID to delete: ");
        String customerId = scanner.nextLine();

        // Retrieve the customer by ID
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            // Delete the customer from the database
            session.delete(customer);
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }

    // Method to view all customers
    private static void viewAllCustomers(Session session) {
        // Retrieve all customers from the database
        List<Customer> customers = session.createQuery("FROM Customer", Customer.class).getResultList();

        // Display all customers
        System.out.println("All Customers:");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getId());
            System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("Mobile Numbers: " + customer.getMobileNumbers());
            System.out.println();
        }
    }

    private static void searchCustomerById(Session session, Scanner scanner) {
        System.out.print("Enter Customer ID to search: ");
        String customerId = scanner.nextLine();

        // Retrieve the customer by ID
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            // Display customer details
            System.out.println("Customer Details:");
            System.out.println("ID: " + customer.getId());
            System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("Mobile Numbers: " + customer.getMobileNumbers());
        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }
}

