package BankingSystem.DAOClasses;

import BankingSystem.Entities.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class EmployeeDAO {
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public void save(Employee employee) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }
            session.save(employee);
            if (transaction != null) {
                transaction.commit();
            }
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Employee getById(int id) {
        return session.get(Employee.class, id);
    }

//    public List<Employee> getAll() {
//        Query query = session.createQuery("SELECT e FROM Employee e");
//        return query.getResultList();
//    }
//
    public void update(Employee employee) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }
            session.update(employee);
            if (transaction != null) {
                transaction.commit();
            }
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Employee employee) {
        Transaction transaction = null;
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }
            session.delete(employee);
            if (transaction != null) {
                transaction.commit();
            }
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

//    public Employee getById(int id) {
//        return session.get(Employee.class, id);
//    }

    public List<Employee> getAll() {
        Query query = session.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }

    public List<Employee> getByBranchId(Session session, int branchId) {
        Query query = session.createQuery("FROM Employee WHERE branch.id = :branchId", Employee.class);
        query.setParameter("branchId", branchId);
        return query.getResultList();
    }
//    public void update(Employee employee) {
//        Transaction transaction = session.beginTransaction();
//        session.update(employee);
//        transaction.commit();
//    }
//
//    public void delete(Employee employee) {
//        Transaction transaction = session.beginTransaction();
//        session.delete(employee);
//        transaction.commit();
//    }
}
