package BankingSystem.Entities;

import BankingSystem.Entities.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    private String branchName;
    private String branchCode;
    private String address;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    // Constructors
    public Branch() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Implementing methods
    public void addEmployee(Employee employee) {
        employee.setBranch(this);
        employees.add(employee);
        System.out.println("Employee " + employee.getEmpName() + " added to branch " + branchName);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        System.out.println("Employee " + employee.getEmpName() + " removed from branch " + branchName);
    }

    public void updateBranchDetails(String branchName, String branchCode, String address) {
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.address = address;
        System.out.println("Branch details updated: Name - " + branchName + ", Code - " + branchCode + ", Address - " + address);
    }

    public List<Account> getAccounts() {
        List<Account> branchAccounts = new ArrayList<>();
        for (Employee employee : employees) {
            branchAccounts.addAll(employee.getAccounts());
        }
        return branchAccounts;
    }
}
