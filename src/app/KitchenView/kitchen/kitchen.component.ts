import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/models/Recipe';

@Component({
  selector: 'app-kitchen',
  templateUrl: './kitchen.component.html',
  styleUrls: ['./kitchen.component.css']
})
export class KitchenComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  recipe: Recipe = {
    id: 0,
    title: '',
    body: '',
    date: '',
    chef: {
      username: '',
      firstName: '',
      lastName: '',
      email: ''
    }
  };

  recipeForUpdate: Recipe = {
    id: 0,
    title: '',
    body: '',
    date: '',
    chef: {
      username: '',
      firstName: '',
      lastName: '',
      email: ''
    }
  };

  show: Boolean = false;

  editRecipe(recipe: Recipe) {
    this.recipeForUpdate=recipe;
  }

  toggle(b: Boolean) {
    this.show=b;
  }

  addItem(recipe: Recipe) {
    this.recipe = recipe;
  }
}
