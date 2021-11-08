package com.revature.services;
import com.revature.data.RecipeDataDAO;
import com.revature.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeDataDAO recipeData;

    @Override
    public Recipe getRecipeById(int id) {
       return recipeData.getRecipeById(id);
    }

    @Override
    public Recipe getRecipeByChef(int id) {
        return null;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeData.getAllRecipes();
    }


    @Override
    public List<Recipe> getAllRecipesByChef(int id) {
         return recipeData.getAllRecipesByChef(id);
    }

    @Override
    public int addRecipe(Recipe recipe) {
        return recipeData.addRecipe(recipe);
    }

    /**
     * Update a Recipe in the database through the DAO
     * @param recipe Recipe to be updated
     * @return 1 is successful, -1 otherwise
     */
    @Override
    public int updateRecipe(Recipe recipe) {
        return recipeData.updateRecipe(recipe);
    }

    /**
     * Recipe to be sent to the DAO to be deleted from the database
     * @param recipe Recipe to be deleted from the database
     * @return 1 is successful, 0 if recipe not deleted, -1 otherwise
     */
    @Override
    public int deleteRecipe(Recipe recipe) {
        return recipeData.deleteRecipe(recipe);
    }
}
