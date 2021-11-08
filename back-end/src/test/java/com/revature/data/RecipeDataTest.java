package com.revature.data;

import com.revature.models.Chef;
import com.revature.models.Recipe;
import com.revature.utilities.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeDataTest {

    private static final Logger logger = LogManager.getLogger(RecipeDataTest.class);
    private static final RecipeData recipeData = new RecipeData();

    @BeforeAll
    static void setup() {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/basic-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();

            transaction = session.beginTransaction();
            buf = new char[1400];
            i = new FileReader("src/test/resources/recipe-test-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void testAddRecipe(){
        Recipe recipe = new Recipe();
        recipe.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        recipe.setDateCreated(Timestamp.valueOf("2020-10-29 07:58:00"));
        recipe.setBody("A new body");
        recipe.setTitle("Title");
        int expected = recipe.getR_id();
        int actual = recipeData.addRecipe(recipe);
        assertEquals(expected, actual);
    }


    @Test
    void testUpdateExistingRecipe() {
        Recipe temp = new Recipe();
        temp.setR_id(2);
        temp.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        temp.setDateCreated(Timestamp.valueOf("2020-09-30 04:30:22"));
        temp.setBody("A new body");
        temp.setTitle("A new title");

        int actual = recipeData.updateRecipe(temp);
        assertEquals(1, actual);
    }

    @Test
    void testUpdateNonExistentRecipe() {
        Recipe temp = new Recipe();
        temp.setR_id(123412);
        temp.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        temp.setDateCreated(Timestamp.valueOf("2020-09-30 04:30:22"));
        temp.setBody("A new body");
        temp.setTitle("A new title");

        int actual = recipeData.updateRecipe(temp);
        assertEquals(-1, actual);
    }

    @Test
    void testUpdateEmptyRecipe() {
        int actual = recipeData.updateRecipe(new Recipe());
        assertEquals(-1, actual);
    }

    @Test
    void testUpdateNullRecipe() {
        int actual = recipeData.updateRecipe(null);
        assertEquals(-1, actual);
    }

    @Test
    void testGetAllRecipes(){
        int expected = 2;
        int actual = recipeData.getAllRecipes().size();
        assertEquals(expected,actual);
    }

   @Test
    void testGetAllRecipesByChef(){
        int expected = 2;
        int actual = recipeData.getAllRecipesByChef(3).size();
        assertEquals(expected,actual);
    }

    @Test
    void testDeleteExistingRecipe() {
        Recipe temp = new Recipe(1, "Recipe1", null, null, null);
        int actual = recipeData.deleteRecipe(temp);
        assertEquals(1, actual);
    }

    @Test
    void testDeleteNonexistentRecipe() {
        Recipe temp = new Recipe(12341234, "Recipe1", null, null, null);
        int actual = recipeData.deleteRecipe(temp);
        assertEquals(0, actual);
    }

    @Test
    void testDeleteNullRecipe() {
        int actual = recipeData.deleteRecipe(null);
        assertEquals(-1, actual);
    }
}
