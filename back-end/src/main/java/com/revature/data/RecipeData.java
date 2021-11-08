package com.revature.data;

import com.revature.models.Chef;
import com.revature.models.Recipe;
import com.revature.utilities.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeData implements RecipeDataDAO {

    @Override
    public Recipe getRecipeById(int id) {
        try(Session s = SessionUtil.getSession()) {
            return s.get(Recipe.class, id);
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        try(Session s = SessionUtil.getSession()){
                return s.createQuery("from Recipe", Recipe.class).list();
            }catch (Exception e) {
            logger.error(e.getMessage());
        }
    return new ArrayList<Recipe>();

    }


    @Override
    public List<Recipe> getAllRecipesByChef(int id) {

        try (Session s = SessionUtil.getSession()) {
            Session session = SessionUtil.getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Recipe> criteriaQuery = builder.createQuery(Recipe.class);
            Root<Recipe> root = criteriaQuery.from(Recipe.class);
            criteriaQuery.select(root);

            criteriaQuery.where(builder.equal(root.get("c_id"), id));
            Query<Recipe> query = session.createQuery(criteriaQuery);
            return query.list();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return new ArrayList<Recipe>();
    }


    /**
     * * Create and add a Recipe to the database
     * @param recipe Recipe to be added
     * @return 1 is successful
     */

    @Override
    public int addRecipe(Recipe recipe) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            int id = (int) session.save(recipe);
            transaction.commit();
            return id;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    /**
     * Update an existing Recipe in the database
     * @param recipe Recipe that was updated
     * @return 1 is successful, -1 otherwise
     */
    @Override
    public int updateRecipe(Recipe recipe) {
        try (Session session = SessionUtil.getSession()){
            Transaction transaction = session.beginTransaction();
            session.update(recipe);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    /**
     * Recipe to be deleted from the database
     * @param recipe Recipe to be deleted from the database
     * @return 1 is successful, 0 if recipe not deleted, -1 otherwise
     */
    @Override
    public int deleteRecipe(Recipe recipe) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete Recipe where r_id = :id");
            query.setParameter("id", recipe.getR_id());
            int i = query.executeUpdate();
            transaction.commit();
            return i;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1;
    }


}
