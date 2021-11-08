package com.revature.services;

import com.revature.data.RecipeData;
import com.revature.data.RecipeDataDAO;
import com.revature.models.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface RecipeService {
    Logger logger = LogManager.getLogger(RecipeServiceImpl.class);
    RecipeDataDAO recipeData = new RecipeData();
    Recipe getRecipeById(int id);
    Recipe getRecipeByChef(int id);
    List<Recipe> getAllRecipes();
    List<Recipe> getAllRecipesByChef(int id);

    int addRecipe(Recipe recipe);
    int updateRecipe(Recipe recipe);
    int deleteRecipe(Recipe recipe);
}
