import { Component, OnInit } from '@angular/core';
import { RecipeService} from 'src/app/services/recipe.service';

@Component({
  selector: 'app-all-recipes-by-chef',
  templateUrl: './all-recipes-by-chef.component.html',
  styleUrls: ['./all-recipes-by-chef.component.css']
})
export class AllRecipesByChefComponent implements OnInit {

  constructor(private service:RecipeService) { }

  RecipeListByChef:any=[];

  ngOnInit(): void {
    this.RecipeListByChef();
  }

  RefreshRecipeListByChef(){
    
      this.service.getRecipeListByChef().subscribe(data=>{this.RecipeListByChef=data});
  }



}
