package com.revature.data;

import com.revature.models.Chef;
import com.revature.utilities.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChefData implements ChefDataDAO {
    public ChefData() {
        super();
    }

    /**
     * Retrieves a chef from the database with the given id
     * @param id unique chef identifier
     * @return the chef with given id, null otherwise
     */
    @Override
    public Chef getChefById(int id) {
        try(Session session = SessionUtil.getSession()) {
            return session.get(Chef.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Chef getChefByUsername(String username) {
        try (Session session = SessionUtil.getSession()) {
            Query<Chef> query = session.createQuery("from Chef where username = :username", Chef.class);
            query.setParameter("username", username);
            return query.getResultList().get(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Chef> getAllChefs() {
        return null;
    }

    @Override
    public int addChef(Chef chef) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            int id = (int) session.save(chef);
            transaction.commit();
            return id;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    @Override
    public int updateChef(Chef chef) {
        return 0;
    }

    @Override
    public int deleteChef(Chef chef) {
        return 0;
    }
}
