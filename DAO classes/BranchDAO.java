package BankingSystem.DAOClasses;

import BankingSystem.Entities.Branch;
import org.hibernate.Session;
import javax.transaction.Transactional;
import java.util.List;

public class BranchDAO {

    private Session session;

    public BranchDAO(Session session) {
        this.session = session;
    }

    @Transactional
    public void save(Branch branch) {
        try {
            session.save(branch);
        } catch (Exception e) {
            System.out.println("Error occurred while saving branch details:");
            e.printStackTrace();
        }
    }

    public Branch getById(int id) {
        try {
            return session.get(Branch.class, id);
        } catch (Exception e) {
            System.out.println("Error occurred while fetching branch with ID: " + id);
            e.printStackTrace();
            return null;
        }
    }

    public List<Branch> getAll() {
        try {
            return session.createQuery("FROM Branch", Branch.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error occurred while fetching all branches:");
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void update(Branch branch) {
        try {
            session.merge(branch);
        } catch (Exception e) {
            System.out.println("Error occurred while updating branch details:");
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(Branch branch) {
        try {
            session.delete(branch);
        } catch (Exception e) {
            System.out.println("Error occurred while deleting branch:");
            e.printStackTrace();
        }
    }
}
