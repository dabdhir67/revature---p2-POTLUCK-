import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddRecipeComponent } from './KitchenView/add-recipe/add-recipe.component';

const routes: Routes = [
  {path: 'add-recipe', component: AddRecipeComponent},
  {path: '', redirectTo: '/add-recipe', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
