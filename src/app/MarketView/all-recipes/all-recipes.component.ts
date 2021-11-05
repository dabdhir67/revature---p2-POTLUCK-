import { Component, OnInit } from '@angular/core';
import { RecipeService } from 'src/app/services/recipe.service';


@Component({
  selector: 'app-all-recipes',
  templateUrl: './all-recipes.component.html',
  styleUrls: ['./all-recipes.component.css']
})
export class AllRecipesComponent implements OnInit {

  constructor(private service:RecipeService) { }

  RecipeList:any = [];

  ngOnInit(): void {
    this.RecipeList();
  }

  refreshRecipeList(){
     this.service.getRecipeList().subscribe(data=>{this.RecipeList=data;});
  }

}