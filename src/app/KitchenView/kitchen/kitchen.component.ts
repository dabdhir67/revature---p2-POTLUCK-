import { Component, OnInit } from '@angular/core';
import { Chef } from 'src/app/models/Chef';
import { Recipe } from 'src/app/models/Recipe';
import { ChefService } from 'src/app/services/chef.service';

@Component({
  selector: 'app-kitchen',
  templateUrl: './kitchen.component.html',
  styleUrls: ['./kitchen.component.css']
})
export class KitchenComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    if (!(sessionStorage.getItem('token'))) {
      window.location.href="/login";
    }
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

  addItem(recipe: Recipe) {
    this.recipe = recipe;
  }

  logout() {
    sessionStorage.clear();
    window.location.href = "/login";
  }
}
