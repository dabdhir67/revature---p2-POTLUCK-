import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

//  readonly APIUrl ="http://localhost:8089/";   // This will change

 /************************** Recipe  ***********************************/ 
 constructor(private http: HttpClient) { }
  
  private DATA_URL: string = "http://localhost:8089/BackEnd/recipe";

  public editRecipe(recipe:Recipe) {
    this.http.put(this.DATA_URL, recipe);
  }

  deleteRecipe(recipe:Recipe) {
    this.http.delete(this.DATA_URL, recipe);
  }

  addRecipe(recipe: Recipe):Observable<Recipe>
  {
    return this.http.post<Recipe>(this.DATA_URL, recipe, {'headers': {'content-type' : 'application/json'}});
  }


  getRecipeList(){
    return this.http.get(this.DATA_URL);
    }
  
    
    getRecipeListByChef(id: number){
    //   return this.http.get(this.DATA_URL+"/${id}");
      const url = `${this.DATA_URL}/${id}`;
      return this.http.get("${this.http://localhost:8089/BackEnd/recipe}/${id}");
    }


   //   return this.http.get<Hero>(url).pipe(
     // tap(_ => this.log(`fetched hero id=${id}`))

}
