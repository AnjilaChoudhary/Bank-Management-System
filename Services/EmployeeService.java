package BankingSystem.Services;

import BankingSystem.Entities.Branch;
import BankingSystem.DAOClasses.EmployeeDAO;
import BankingSystem.Entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final SessionFactory sessionFactory;

    public EmployeeService(EmployeeDAO employeeDAO, SessionFactory sessionFactory) {
        this.employeeDAO = employeeDAO;
        this.sessionFactory = sessionFactory;
    }

    public void saveEmployee(Employee employee, int branchId) {
        // Check if the branch with the given branchId exists
        Branch branch = retrieveBranchById(branchId);
        if (branch == null) {
            // Handle the case where the branch does not exist
            System.out.println("Error: Branch with ID " + branchId + " does not exist.");
            return;
        }

        // Associate the branch with the employee
        employee.setBranch(branch);

        // Open a new session and transaction
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            employeeDAO.setSession(session);
            employeeDAO.save(employee);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        try (Session session = sessionFactory.openSession()) {
            employeeDAO.setSession(session);
            return employeeDAO.getById(id);
        }
    }

    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            employeeDAO.setSession(session);
            return employeeDAO.getAll();
        }
    }

    public void updateEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            employeeDAO.setSession(session);
            employeeDAO.update(employee);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            employeeDAO.setSession(session);
            employeeDAO.delete(employee);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private Branch retrieveBranchById(int branchId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Branch.class, branchId);
        }
    }

    public static void main(String[] args) {
        // Create a Hibernate Configuration instance and configure it
        Configuration configuration = new Configuration();
        configuration.configure("hibernate3.cfg.xml");

        // Build SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create EmployeeDAO instance
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Create EmployeeService instance
        EmployeeService employeeService = new EmployeeService(employeeDAO, sessionFactory);

        // Define or obtain the branchId
        int branchId = 2; // For example

        // Creating an Employee and setting the branch
        Employee employee = new Employee();
        employee.setEmpName("Aarfa");
        employee.setMobileNo("1234567890");
        employee.setAddress("Alpha 1 ");
        // Get the branch based on branchId (This part is application-specific)
        Branch branch = employeeService.retrieveBranchById(branchId);
        if (branch != null) {
            employee.setBranch(branch);
        } else {
            System.out.println("Error: Branch with ID " + branchId + " does not exist.");
            return;
        }

        // Saving the Employee
        employeeService.saveEmployee(employee, branchId);
        System.out.println("Employee saved: " + employee);

        // Retrieving all Employees
        List<Employee> employees = employeeService.getAllEmployees();
        System.out.println("All Employees:");
        for (Employee emp : employees) {
            // Get the branch of each employee
            Branch empBranch = emp.getBranch();
            if (empBranch != null) {
                System.out.println("Employee: " + emp.getEmpName() + ", Branch: " + empBranch.getBranchName());
            } else {
                System.out.println("Employee: " + emp.getEmpName() + ", Branch: No branch assigned");
            }
        }

        // Close the SessionFactory
        sessionFactory.close();
    }
}


//public class EmployeeService {
//
//    private final EmployeeDAO employeeDAO;
//    private final SessionFactory sessionFactory;
//
//    public EmployeeService(EmployeeDAO employeeDAO, SessionFactory sessionFactory) {
//        this.employeeDAO = employeeDAO;
//        this.sessionFactory = sessionFactory;
//    }
//
//    public void saveEmployee(Employee employee) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        employeeDAO.setSession(session);
//        employeeDAO.save(employee);
//        transaction.commit();
//        session.close();
//    }
//
//    public Employee getEmployeeById(int id) {
//        Session session = sessionFactory.openSession();
//        employeeDAO.setSession(session);
//        Employee employee = employeeDAO.getById(id);
//        session.close();
//        return employee;
//    }
//
//    public List<Employee> getAllEmployees() {
//        Session session = sessionFactory.openSession();
//        employeeDAO.setSession(session);
//        List<Employee> employees = employeeDAO.getAll();
//        session.close();
//        return employees;
//    }
//
//    public void updateEmployee(Employee employee) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        employeeDAO.setSession(session);
//        employeeDAO.update(employee);
//        transaction.commit();
//        session.close();
//    }
//
//    public void deleteEmployee(Employee employee) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        employeeDAO.setSession(session);
//        employeeDAO.delete(employee);
//        transaction.commit();
//        session.close();
//    }
//
//    // Additional method
//
//
//    public static void main(String[] args) {
//        // Create a Hibernate Configuration instance and configure it
//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate3.cfg.xml");
//
//        // Build SessionFactory
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//
//        // Create EmployeeDAO instance
//        EmployeeDAO employeeDAO = new EmployeeDAO();
//
//        // Create EmployeeService instance
//        EmployeeService employeeService = new EmployeeService(employeeDAO, sessionFactory);
//
//        // Define or obtain the branchId
//        int branchId = 1; // For example
//
//        // Creating an Employee and setting the branch
//        Employee employee = new Employee();
//        employee.setEmpName("John Doe");
//        employee.setMobileNo("1234567890");
//        employee.setAddress("123 Main St");
//        // Get the branch based on branchId (This part is application-specific)
//        Branch branch = retrieveBranchById(branchId); // Implement this method
//        employee.setBranch(branch);
//
//        // Saving the Employee
//        employeeService.saveEmployee(employee);
//        System.out.println("Employee saved: " + employee);
//
//        // Retrieving all Employees
//        List<Employee> employees = employeeService.getAllEmployees();
//        System.out.println("All Employees:");
//        for (Employee emp : employees) {
//            // Get the branch of each employee
//            Branch empBranch = emp.getBranch();
//            if (empBranch != null) {
//                System.out.println("Employee: " + emp.getEmpName() + ", Branch: " + empBranch.getBranchName());
//            } else {
//                System.out.println("Employee: " + emp.getEmpName() + ", Branch: No branch assigned");
//            }
//
//        }
//
//        // Updating an Employee
//        Employee employeeToUpdate = employeeService.getEmployeeById(employee.getEmpId());
//        employeeToUpdate.setAddress("456 Elm St");
//        employeeService.updateEmployee(employeeToUpdate);
//        System.out.println("Employee updated: " + employeeToUpdate);
//
//        // Deleting an Employee
//        employeeService.deleteEmployee(employeeToUpdate);
//        System.out.println("Employee deleted: " + employeeToUpdate);
//
//        // Retrieving all Employees after deletion
//        employees = employeeService.getAllEmployees();
//        System.out.println("Remaining Employees:");
//        for (Employee emp : employees) {
//            // Get the branch of each employee
//            Branch empBranch = emp.getBranch();
//            System.out.println("Employee: " + emp.getEmpName() + ", Branch: " + empBranch.getBranchName());
//        }
//
//        // Close the SessionFactory
//        sessionFactory.close();
//    }
//
//    // Method to retrieve branch by ID (This part is application-specific)
//    private static Branch retrieveBranchById(int branchId) {
//        // Implement this method based on your application logic
//        // For demonstration purposes, let's assume we retrieve a branch from the database
//        // You can replace this with your actual logic to retrieve the branch
//        // Dummy implementation:
//        Branch branch = new Branch();
//        branch.setId(branchId);
//        branch.setBranchName("Sample Branch");
//        return branch;
//    }
//}
