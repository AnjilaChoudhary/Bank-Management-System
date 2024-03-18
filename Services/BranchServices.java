package BankingSystem.Services;
import BankingSystem.Entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import BankingSystem.DAOClasses.BranchDAO;
import BankingSystem.Entities.Branch;
import java.util.List;
import java.util.Scanner;

public class BranchServices {

    public static void main(String[] args) {
        // Initialize variables
        Session session = null;
        Scanner scanner = new Scanner(System.in);
        Transaction transaction = null;

        try {
            // Create a SessionFactory
            SessionFactory sessionFactory = new Configuration().configure("hibernate3.cfg.xml").buildSessionFactory();
            // Open a new session
            session = sessionFactory.openSession();
            // Begin a new transaction
            transaction = session.beginTransaction();

            // Create a BranchDAO instance to perform database operations
            BranchDAO branchDAO = new BranchDAO(session);

            // Display menu options
            System.out.println("Branch Service");
            System.out.println("1. Add Branch");
            System.out.println("2. Update Branch");
            System.out.println("3. Delete Branch");
            System.out.println("4. View All Branches");
            System.out.println("5. View Branch by ID");
            System.out.print("Enter your choice: ");

            // Read user input for choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Process user choice
            switch (choice) {
                case 1:
                    addBranch(session, scanner, branchDAO); // Call addBranch method
                    break;
                case 2:
                    updateBranch(session, scanner, branchDAO); // Call updateBranch method
                    break;
                case 3:
                    deleteBranch(session, scanner, branchDAO); // Call deleteBranch method
                    break;
                case 4:
                    viewAllBranches(branchDAO); // Call viewAllBranches method
                    break;
                case 5:
                    viewBranchById(session, scanner, branchDAO); // Call viewBranchById method
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
            // Close the session
            if (session != null) {
                session.close();
            }
        }
    }

    // Method to add a new branch
    private static void addBranch(Session session, Scanner scanner, BranchDAO branchDAO) {
        // Prompt user for branch details
        System.out.println("Enter branch details:");
        System.out.print("Branch Name: ");
        String branchName = scanner.nextLine();
        System.out.print("Branch Code: ");
        String branchCode = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        // Create a new Branch object with user-provided details
        Branch branch = new Branch();
        branch.setBranchName(branchName);
        branch.setBranchCode(branchCode);
        branch.setAddress(address);

        // Save the branch in the database
        branchDAO.save(branch);
        System.out.println("Branch added successfully.");
    }

    // Method to update an existing branch
    private static void updateBranch(Session session, Scanner scanner, BranchDAO branchDAO) {
        // Prompt user for branch ID to update
        System.out.print("Enter branch ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the branch from the database by ID
        Branch branch = branchDAO.getById(id);
        if (branch != null) {
            // Prompt user for new details
            System.out.println("Enter new details:");
            System.out.print("Branch Name: ");
            String branchName = scanner.nextLine();
            System.out.print("Branch Code: ");
            String branchCode = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();

            // Update the branch with new details
            branch.setBranchName(branchName);
            branch.setBranchCode(branchCode);
            branch.setAddress(address);

            // Update the branch in the database
            branchDAO.update(branch);
            System.out.println("Branch updated successfully.");
        } else {
            System.out.println("Branch not found with ID: " + id);
        }
    }

    // Method to delete an existing branch
    private static void deleteBranch(Session session, Scanner scanner, BranchDAO branchDAO) {
        // Prompt user for branch ID to delete
        System.out.print("Enter branch ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the branch from the database by ID
        Branch branch = branchDAO.getById(id);
        if (branch != null) {
            // Delete the branch from the database
            branchDAO.delete(branch);
            System.out.println("Branch deleted successfully.");
        } else {
            System.out.println("Branch not found with ID: " + id);
        }
    }

    // Method to view all branches
    private static void viewAllBranches(BranchDAO branchDAO) {
        // Retrieve all branches from the database
        List<Branch> branches = branchDAO.getAll();
        // Display all branches
        System.out.println("All Branches:");
        for (Branch branch : branches) {
            System.out.println("ID: " + branch.getId() + ", Name: " + branch.getBranchName() + ", Code: " + branch.getBranchCode() + ", Address: " + branch.getAddress());
        }
    }

    // Method to view branch details by ID
    private static void viewBranchById(Session session, Scanner scanner, BranchDAO branchDAO) {
        // Prompt user for branch ID
        System.out.print("Enter branch ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Retrieve the branch from the database by ID
        Branch branch = branchDAO.getById(id);
        if (branch != null) {
            // Display branch details
            System.out.println("Branch Details:");
            System.out.println("ID: " + branch.getId());
            System.out.println("Name: " + branch.getBranchName());
            System.out.println("Code: " + branch.getBranchCode());
            System.out.println("Address: " + branch.getAddress());
            // Display employees associated with the branch (if any)
            List<Employee> employees = branch.getEmployees();
            System.out.println("Employees:");
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getEmpId() + ", Name: " + employee.getEmpName());
            }
        } else {
            System.out.println("Branch not found with ID: " + id);
        }
    }
}
