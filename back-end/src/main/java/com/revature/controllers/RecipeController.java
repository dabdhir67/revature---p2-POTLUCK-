package com.revature.controllers;
import com.revature.models.Chef;
import com.revature.models.Recipe;
import com.revature.services.RecipeService;
import com.revature.utilities.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PutMapping
    public ResponseEntity<String> editRecipe(@RequestBody Recipe recipe, HttpServletRequest req) {
        Chef chef = (Chef) req.getAttribute("chef");
        chef.setC_id((Integer) req.getAttribute("id"));
        chef.setPasskey((String) req.getAttribute("passkey"));

        if (recipe == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        recipe.setC_id(chef);
        int check = recipeService.updateRecipe(recipe);

        if (check == 1) {
            return new ResponseEntity<>("Recipe has been updated", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("A problem has occurred when updating the recipe", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecipe(@RequestBody Recipe recipe, HttpServletRequest req) {
        Chef chef = (Chef) req.getAttribute("chef");
        chef.setC_id((Integer) req.getAttribute("id"));
        chef.setPasskey((String) req.getAttribute("passkey"));

        int check = recipeService.deleteRecipe(recipe);
        if (check == 0) {
            return new ResponseEntity<>("No recipe to be deleted", HttpStatus.NO_CONTENT);
        } else if (check == -1) {
            return new ResponseEntity<>("A problem has occurred when deleting the recipe", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Delete successful", HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestHeader String title, @RequestBody String body, HttpServletRequest req){
        Chef chef = (Chef) req.getAttribute("chef");
        chef.setC_id((Integer) req.getAttribute("id"));
        chef.setPasskey((String) req.getAttribute("passkey"));

        Recipe recipe = new Recipe(SecurityUtil.getId(), title, body, Timestamp.valueOf(LocalDateTime.now()), chef);

        int i = recipeService.addRecipe(recipe);
        if (i > 0) {
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipes(@RequestHeader String getAll, HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        if (Boolean.parseBoolean(getAll)) {
            return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(recipeService.getAllRecipesByChef(id), HttpStatus.OK);
        }
    }
}


