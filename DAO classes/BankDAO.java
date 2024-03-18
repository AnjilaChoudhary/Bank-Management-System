package BankingSystem.DAOClasses;

import BankingSystem.Entities.Bank;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.transaction.Transactional;
import java.util.List;

public class BankDAO {

    private Session session;

    public BankDAO(Session session) {
        this.session = session;
    }

    @Transactional
    public void save(Bank bank) {
        try {
            session.save(bank);
        } catch (Exception e) {
            System.out.println("Error occurred while saving bank details:");
            e.printStackTrace();
        }
    }

    public Bank getById(int id) {
        try {
            return session.get(Bank.class, id);
        } catch (Exception e) {
            System.out.println("Error occurred while fetching bank with ID: " + id);
            e.printStackTrace();
            return null;
        }
    }

    public List<Bank> getAll() {
        try {
            Query<Bank> query = session.createQuery("FROM Bank", Bank.class);
            return query.list();
        } catch (Exception e) {
            System.out.println("Error occurred while fetching all banks:");
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void update(Bank bank) {
        try {
            session.update(bank);
        } catch (Exception e) {
            System.out.println("Error occurred while updating bank details:");
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(Bank bank) {
        try {
            session.delete(bank);
        } catch (Exception e) {
            System.out.println("Error occurred while deleting bank:");
            e.printStackTrace();
        }
    }

    public Bank getBankByDetails(String bankName, String code, String city, String address) {
        try {
            Query<Bank> query = session.createQuery("FROM Bank WHERE bankName = :bankName AND code = :code AND city = :city AND address = :address", Bank.class);
            query.setParameter("bankName", bankName);
            query.setParameter("code", code);
            query.setParameter("city", city);
            query.setParameter("address", address);
            return query.uniqueResult();
        } catch (Exception e) {
            System.out.println("Error occurred while fetching bank by details:");
            e.printStackTrace();
            return null;
        }
    }
}
