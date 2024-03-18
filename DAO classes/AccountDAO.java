package BankingSystem.Entities;

import javax.persistence.*;


@Entity
public  class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNo;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // Constructors
    public Account() {
    }

    // Getters and Setters
    public int getAccountNo() {
        return accountNo;
    }


    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Implementing methods
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Amount " + amount + " deposited. Current balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Amount " + amount + " withdrawn. Current balance: " + balance);
        } else {
            System.out.println("Insufficient funds");
        }
    }


    public void transfer(Account destinationAccount, double amount) {
        if (balance >= amount) {
            withdraw(amount);
            destinationAccount.deposit(amount);
            System.out.println("Amount " + amount + " transferred to account " + destinationAccount.getAccountNo());
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public double checkBalance() {
        return balance;
    }

    public void updateAccountDetails(AccountType accountType) {
        this.accountType = accountType;
        System.out.println("Account type updated to " + accountType);
    }


    public enum AccountType {
        SAVINGS,
        CURRENT
    }
}

