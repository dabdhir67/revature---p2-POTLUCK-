import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  
  addRecipe(recipe: Recipe) {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient) { }
  
  // addRecipe(recipe:Recipe): Observable<Recipe[]>{

}
