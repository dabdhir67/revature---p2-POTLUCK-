import { Component, Input, Output, OnInit, OnChanges, SimpleChanges, EventEmitter } from '@angular/core';
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
  @Input() newRecipe?: Recipe;

  @Output() edit: EventEmitter<Recipe> = new EventEmitter();
  @Output() show: EventEmitter<Boolean> = new EventEmitter();

  ngOnInit(): void {
    this.recipeService.getChefRecipeList().subscribe(
      result => { 
        this.recipeList = result
    });
  }

  openEdit(recipe: Recipe) {
    this.edit.emit(recipe);
    this.show.emit(true);
  }

  deleteRecipe(recipe: Recipe): void {
    this.recipeList.forEach((element,index)=>{
      if(element==recipe) this.recipeList.splice(index,1);
   });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.newRecipe) this.recipeList.push(this.newRecipe);
  }
}
