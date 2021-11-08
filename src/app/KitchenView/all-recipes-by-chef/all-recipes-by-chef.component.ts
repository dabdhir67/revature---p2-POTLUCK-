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
  selected: Recipe | undefined;
  editRecipe: Recipe | undefined;
  @Input() newRecipe?: Recipe;

  @Output() edit: EventEmitter<Recipe> = new EventEmitter();
  @Output() show: EventEmitter<Boolean> = new EventEmitter();

  ngOnInit(): void {
    this.recipeService.getChefRecipeList().subscribe(
      result => { 
        this.recipeList = result;
        this.selected = this.recipeList[0];
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
   this.selected = this.recipeList[0];
  }

  rearrange(recipe: Recipe) {
    const i = this.recipeList.indexOf(recipe);
    this.recipeList.splice(i, 1);
    this.editRecipe = recipe;
  }

  saveEdit() {
    console.log(this.editRecipe);
    if (this.editRecipe && this.editRecipe.title != '' && this.editRecipe.body != '') {
      this.recipeService.editRecipe(this.editRecipe);
      this.recipeList.push(this.editRecipe);
      this.selected = this.editRecipe;
      this.editRecipe = undefined;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.newRecipe && this.newRecipe.title !== '' && this.newRecipe.body !== '') 
      this.recipeList.push(this.newRecipe);
      this.selected = this.newRecipe;
  }
}
