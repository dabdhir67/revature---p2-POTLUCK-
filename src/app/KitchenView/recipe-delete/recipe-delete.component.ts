import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { SendChef } from 'src/app/models/Chef';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-delete',
  templateUrl: './recipe-delete.component.html',
  styleUrls: ['./recipe-delete.component.css']
})
export class RecipeDeleteComponent implements OnInit {

  @Input()
  //recipe!: Recipe;

  date: Date = new Date(2021, 11, 5);
  chef: SendChef = {username: "user1", password: "password", firstName: "One", lastName: "First", email: "Email1"};
  recipe: Recipe = new Recipe(1,"Recipe1","Test1", this.date, this.chef);

  deleteRecipe(){
    this.recipeService.deleteRecipe(this.recipe).subscribe(
      result=>{
        console.log(result)
      }
    );
    console.log("Delete?");
  }

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
  }

}
