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
    title: '',
    body: '',
    date: ''
  };

  addItem(recipe: Recipe) {
    this.recipe = recipe;
  }
}
