package BankingSystem.Entities;

import BankingSystem.Entities.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bankName;
    private String code;
    private String city;
    private String address;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Customer> customers = new ArrayList<>();

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();

    // Constructors
    public Bank() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }



    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    // Implementing methods
    public void addCustomer(Customer customer) {
        customer.setBank(this);
        customers.add(customer);
        System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " added to bank " + bankName);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        System.out.println("Customer " + customer.getFirstName() + " " + customer.getLastName() + " removed from bank " + bankName);
    }

    public void addBranch(Branch branch) {
        branch.setBank(this);
        branches.add(branch);
        System.out.println("Branch " + branch.getBranchName() + " added to bank " + bankName);
    }

    public void removeBranch(Branch branch) {
        branches.remove(branch);
        System.out.println("Branch " + branch.getBranchName() + " removed from bank " + bankName);
    }

    public List<Account> getCustomerAccounts(Customer customer) {
        return customer.getAccounts();
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
