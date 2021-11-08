import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';
import { RecipeService } from 'src/app/services/recipe.service';
import { convertCompilerOptionsFromJson } from 'typescript';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {

  @Input()
  updateRecipe!: Recipe;
  @Input()
  show: Boolean = false;

  @Output() toggle: EventEmitter<Boolean> = new EventEmitter();

  title: string = "";
  body: string = "";

  editRecipe() {
    this.updateRecipe.title = this.title;
    this.updateRecipe.body = this.body;
    this.recipeService.editRecipe(this.updateRecipe);
    this.show = false;
    this.toggle.emit(this.show);
  }

  cancelEdit(){
    this.show = false;
    this.toggle.emit(this.show);
  }

  constructor(private recipeService: RecipeService) { }

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges): void {
    this.title=this.updateRecipe.title;
    this.body=this.updateRecipe.body;
  }
}
