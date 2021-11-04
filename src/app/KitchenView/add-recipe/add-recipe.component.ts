import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  constructor(public recipeService: RecipeService) { }

  @Input()
  recipe!: Recipe;


  title: string = "";
  body: string = "";

  ngOnInit(): void {
  }


  addRecipe(){
      console.log(`adding recipe: ${this.title}, ${this.body}}`);
  
      this.recipe.title = this.title;
      this.recipe.body = this.body;
      this.recipeService.addRecipe(this.recipe);  
  }

}
