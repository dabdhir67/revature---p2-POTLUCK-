import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipe-delete',
  templateUrl: './recipe-delete.component.html',
  styleUrls: ['./recipe-delete.component.css']
})
export class RecipeDeleteComponent implements OnInit {

  @Input() recipe!: Recipe;

  @Output() delete: EventEmitter<Recipe> = new EventEmitter();

  deleteRecipe(){
    this.recipeService.deleteRecipe(this.recipe).subscribe(
      result => {
        console.log(result);
        this.delete.emit(this.recipe);
      });
  }

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
  }

}
