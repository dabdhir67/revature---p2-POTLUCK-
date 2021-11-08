import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrls: ['./market.component.css']
})
export class MarketComponent implements OnInit {

  constructor(private recipeService: RecipeService) { }

  recipeList: Recipe[] = [];

  ngOnInit(): void {
    if (!(sessionStorage.getItem('token'))) {
      window.location.href="/login";
    }
    this.recipeService.getAllRecipes().subscribe(
      (result: Recipe[]) => {this.recipeList = result; console.log(this.recipeList[0].chef);}
    );
  }

}
