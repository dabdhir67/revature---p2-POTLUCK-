import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private httpClient: HttpClient) { }

  private URL: string = "http://localhost:8080/BackEnd/recipe";

  editRecipe(recipe:Recipe) {
    this.httpClient.put(this.URL, recipe, { headers: {"Authorization" : `${sessionStorage.getItem('token')}`}});
  }

  deleteRecipe(recipe:Recipe) {
    this.httpClient.delete(this.URL, recipe);
  }

  addRecipe(title: string, body: string): Observable<Recipe>{
    return this.httpClient.post<Recipe>(this.URL, null, { headers: {
      "Authorization" : `${sessionStorage.getItem('token')}`,
      "title" : `${title}`,
      "body" : `${body}`
    }});
  }

  getAllRecipes(){
    return this.httpClient.get<Recipe[]>(this.URL, { headers: {
      "Authorization" : `${sessionStorage.getItem('token')}`,
      "getAll" : "true"}
    });
  }

    
  getChefRecipeList(): Observable<Recipe[]>{
    return this.httpClient.get<Recipe[]>(this.URL, { headers: {
      "Authorization" : `${sessionStorage.getItem('token')}`,
      "getAll" : "false"}
    });
  }
}
