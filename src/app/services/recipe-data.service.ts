import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeDataService {

  private DATA_URL: string = "http://localhost:8080/BackEnd/recipe";

  public editRecipe(recipe:Recipe) {
    //update the Recipe in database
  }

  public deleteRecipe(recipe:Recipe){
    this.http.delete(this.DATA_URL, recipe);
  }

  constructor(private http: HttpClient) { }
}
