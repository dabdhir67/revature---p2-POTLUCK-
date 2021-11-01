import { Component, Input, OnInit } from '@angular/core';
import { Chef } from 'src/app/models/Chef';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeDataService } from 'src/app/services/recipe-data.service';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  @Input()
  recipe!: Recipe;

  recipeTitle:string = this.recipe.title;
  recipeBody:string = this.recipe.body;

  editRecipe() {
    this.recipe.title = this.recipeTitle;
    this.recipe.body = this.recipeBody;
    this.recipeService.editRecipe(this.recipe);
  }

  constructor(private recipeService: RecipeDataService) { }

  ngOnInit(): void { }

}
