package BankingSystem.Entities;

import BankingSystem.Entities.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    private String firstName;
    private String lastName;
    private String customerId;

    @ElementCollection
    @CollectionTable(name = "customer_mobile", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "mobile_no")
    private List<String> mobileNumbers = new ArrayList<>();

    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Constructors
    public Customer() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<String> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Implementing methods
    public void openAccount(Account account) {
        account.setCustomer(this);
        accounts.add(account);
        System.out.println("Account opened for customer " + firstName + " " + lastName);
    }

    public void closeAccount(Account account) {
        accounts.remove(account);
        System.out.println("Account closed for customer " + firstName + " " + lastName);
    }

    public void updateDetails(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        System.out.println("Customer details updated: Name - " + firstName + " " + lastName + ", Address - " + address);
    }

    public double getAccountBalance(Account account) {
        return account.getBalance();
    }


}
