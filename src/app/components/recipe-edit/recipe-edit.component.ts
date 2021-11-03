import { Component, Input, OnInit } from '@angular/core';
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

  title: string = "";
  body: string = "";

  editRecipe() {
    this.recipe.title = this.title;
    this.recipe.body = this.body;
    this.recipeService.editRecipe(this.recipe);
  }

  constructor(private recipeService: RecipeDataService) { }

  ngOnInit(): void { }

}
