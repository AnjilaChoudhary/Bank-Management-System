package BankingSystem.Entities;

import BankingSystem.Entities.Account;
import BankingSystem.Entities.Branch;
import BankingSystem.Entities.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    private String empName;
    private String mobileNo;
    private String address;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    // Constructors
    public Employee() {
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // Implementing methods
    public void assignAccount(Account account) {
        account.setEmployee(this);
        accounts.add(account);
        System.out.println("Account assigned to employee " + empName);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        System.out.println("Account removed from employee " + empName);
    }

    public void updateEmployeeDetails(String empName, String mobileNo, String address) {
        this.empName = empName;
        this.mobileNo = mobileNo;
        this.address = address;
        System.out.println("Employee details updated: Name - " + empName + ", Mobile No - " + mobileNo + ", Address - " + address);
    }

    public List<Account> getCustomerAccounts(Customer customer) {
        List<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getCustomer().equals(customer)) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }

    public int getId() {
        return empId;
    }
}
