package com.revature.services;

import com.revature.data.RecipeDataDAO;
import com.revature.models.Chef;
import com.revature.models.Recipe;
import com.revature.utilities.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    @Mock
    private RecipeDataDAO recipeData;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup() {
        try(Session session = SessionUtil.getSession()) {
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
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Update an existing recipe")
    void testUpdateExistingRecipe() {
        Recipe temp = new Recipe();
        temp.setR_id(2);
        temp.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        temp.setDateCreated(Timestamp.valueOf("2020-09-30 04:30:22"));
        temp.setBody("A new body");
        temp.setTitle("A new title");

        when(recipeData.updateRecipe(temp)).thenReturn(1);
        int actual = recipeService.updateRecipe(temp);
        assertEquals(1, actual);
    }

    @Test
    @DisplayName("Update an nonexisting recipe")
    void testUpdateNonExistentRecipe() {
        Recipe temp = new Recipe();
        temp.setR_id(123412);
        temp.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        temp.setDateCreated(Timestamp.valueOf("2020-09-30 04:30:22"));
        temp.setBody("A new body");
        temp.setTitle("A new title");

        when(recipeData.updateRecipe(temp)).thenReturn(-1);
        int actual = recipeService.updateRecipe(temp);
        assertEquals(-1, actual);
    }

    @Test
    void testGetAllRecipes(){
    //    List<Recipe> recipe = Mockito.mock(List.class);
        int expected  = 2;
  //      List<Recipe> recipes = recipeData.getAllRecipes();
        ArrayList<Recipe> recipe = new ArrayList<>();
        recipe.add(new Recipe());
        recipe.add(new Recipe());
        when(recipeData.getAllRecipes()).thenReturn(recipe);
        int actual = recipeService.getAllRecipes().size();
        assertEquals(expected,actual);
    }


    @Test
    void testGetAllRecipesByChef(){  // no passed
        int id =1;
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        when(recipeData.getAllRecipesByChef(id)).thenReturn(recipes);
        int actual = recipeService.getAllRecipesByChef(1).size();
        assertEquals(1,actual);
    }

    @Test
    @DisplayName("Update with an empty recipe")
    void testUpdateEmptyRecipe() {
        Recipe recipe = new Recipe();
        when(recipeData.updateRecipe(recipe)).thenReturn(-1);
        int actual = recipeService.updateRecipe(recipe);
        assertEquals(-1, actual);
    }

    @Test
    @DisplayName("Update with a null recipe")
    void testUpdateNullRecipe() {
        Recipe recipe = null;
        when(recipeData.updateRecipe(recipe)).thenReturn(-1);
        int actual = recipeService.updateRecipe(recipe);
        assertEquals(-1, actual);
    }

    @DisplayName("Update recipe")
    void testUpdateRecipe() {
        Recipe recipe = new Recipe();
        recipeService.updateRecipe(recipe);
        recipeService.updateRecipe(recipe);
        verify(recipeData, times(2)).updateRecipe(recipe);
    }

    @Test
    @DisplayName("Delete a recipe")
    void testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipeService.deleteRecipe(recipe);
        recipeService.deleteRecipe(recipe);
        recipeService.deleteRecipe(recipe);

        verify(recipeData, times(3)).deleteRecipe(recipe);
    }

    @Test
    @DisplayName("Insert a Recipe Object")
    void addRecipe(){
        Recipe recipe = new Recipe();
        recipe.setC_id(new Chef(1, "user1", "password", "One", "First", "Email1"));
        recipe.setDateCreated(Timestamp.valueOf("2020-10-29 07:58:00"));
        recipe.setBody("A new body");
        recipe.setTitle("Title");
        int expected = recipe.getR_id();
        when(recipeData.addRecipe(recipe)).thenReturn(recipe.getR_id());
        int actual = recipeService.addRecipe(recipe);
        assertEquals(expected,actual);
    }

}
