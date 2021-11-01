import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeDataService {

  private DATA_URL: string = "somethingsomething";

  public editRecipe(recipe:Recipe) {
    //update the Recipe in database
  }

  public deleteRecipe(recipe:Recipe) {
    //delete the Recipe in database
  }

  constructor(private http: HttpClient) { }
}
