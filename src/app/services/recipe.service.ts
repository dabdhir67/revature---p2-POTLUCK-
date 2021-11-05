import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private DATA_URL: string = "http://localhost:8080/BackEnd/recipe";

  public editRecipe(recipe:Recipe) {
    this.http.put(this.DATA_URL, recipe);
  }

  public deleteRecipe(recipe:Recipe) {
    const headers = new HttpHeaders();
    headers.set();
    return this.http.delete(this.DATA_URL, recipe);
  }

  addRecipe(recipe: Recipe) {
    throw new Error('Method not implemented.');
  }

  constructor(private http: HttpClient) { }
  
  // addRecipe(recipe:Recipe): Observable<Recipe[]>{

}
