import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-delete',
  templateUrl: './recipe-delete.component.html',
  styleUrls: ['./recipe-delete.component.css']
})
export class RecipeDeleteComponent implements OnInit {

  @Input()
  recipe!: Recipe;

  deleteRecipe(){
    this.recipeService.deleteRecipe(this.recipe);
  }

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
  }

}
