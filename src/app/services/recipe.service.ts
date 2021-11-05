import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
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

  deleteRecipe(recipe: Recipe) {
    console.log(recipe);
    const options = {
      headers: new HttpHeaders({
        "Authorization" : `${sessionStorage.getItem('token')}`
      }),
      body: recipe,
      responseType:'text' as 'json'
    };
    return this.httpClient.delete(this.URL, options);
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
