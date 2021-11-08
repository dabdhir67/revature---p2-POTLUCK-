import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  constructor(private recipeService: RecipeService) { }

  @Output() recipeEmit: EventEmitter<Recipe> = new EventEmitter<Recipe>();

  title: string = "";
  body: string = "";

  ngOnInit(): void {
  }

  addRecipe(){
    if (this.title.length > 0 || this.body.length > 0) {
      this.recipeService.addRecipe(this.title, this.body).subscribe(
        result => this.recipeEmit.emit(result)
      );
      this.title = '';
      this.body = '';
    } else {
      if (this.title.length == 0) {
        document.getElementById('title')?.classList.add('is-invalid');
      }
      if (this.body.length == 0) {
        document.getElementById('body')?.classList.add('is-invalid');
      }
    }
  }
}
