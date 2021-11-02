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

  title: string = "";
  body: string = "";

  ngOnInit(): void {
  }


  addRecipe(){
    // console.log(`adding recipe: ${this.title}, ${this.body}}`);
    // const recipe = {
    //   title: this.title,
    //   body: this.body,
    //   date: timeStamp.valueOf("2020-10-29 07:58:00"),
    //   chef: null
    // }
    // this.recipeService.addRecipe(recipe)
  }

}
