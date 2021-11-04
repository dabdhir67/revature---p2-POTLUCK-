import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../models/Recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  readonly APIUrl ="http://localhost:8089/";   // This will change

 /************************** Recipe  ***********************************/ 
 getRecipeList():Observable<any[]>{
  return this.httpClient.get<any>(this.APIUrl+'/Recipe');
  }

  
  getRecipeListByChef():Observable<any[]>{
    return this.httpClient.get<any>(this.APIUrl+'/Recipe/Chef');
  }

  
  addRecipe(recipe: Recipe) {
    throw new Error('Method not implemented.');
  }

  constructor(private httpClient: HttpClient) { }
  
  // addRecipe(recipe:Recipe): Observable<Recipe[]>{

}
