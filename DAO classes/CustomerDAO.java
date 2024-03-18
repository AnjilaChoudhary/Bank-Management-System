package BankingSystem.DAOClasses;
import BankingSystem.Entities.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public class CustomerDAO {

    private final Session session;

    public CustomerDAO(Session session) {
        this.session = session;
    }

    @Transactional
    public void save(Customer customer) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Customer getById(int id) {
        return session.find(Customer.class, id);
    }

    public List<Customer> getAll() {
        return session.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Transactional
    public void update(Customer customer) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(Customer customer) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
