import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService} from 'src/app/services/recipe.service';

@Component({
  selector: 'app-all-recipes-by-chef',
  templateUrl: './all-recipes-by-chef.component.html',
  styleUrls: ['./all-recipes-by-chef.component.css']
})
export class AllRecipesByChefComponent implements OnInit {

  constructor(private recipeService:RecipeService) { }

  recipeList: Recipe[] = [];

  ngOnInit(): void {
    this.recipeService.getChefRecipeList().subscribe(
      result => { 
        this.recipeList = result
    });
  }



}
