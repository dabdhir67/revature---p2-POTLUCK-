package com.revature.data;

import com.revature.models.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface RecipeDataDAO {
    Logger logger = LogManager.getLogger(RecipeData.class);
    Recipe getRecipeById(int id);
    List<Recipe> getAllRecipes();
    List<Recipe> getAllRecipesByChef(int id);

    int addRecipe(Recipe recipe);
    int updateRecipe(Recipe recipe);
    int deleteRecipe(Recipe recipe);
}
