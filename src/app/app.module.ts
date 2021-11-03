import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
<<<<<<< HEAD
import { RecipeEditComponent } from './recipe/recipe-edit/recipe-edit.component';
import { RecipeDeleteComponent } from './recipe/recipe-delete/recipe-delete.component';

import { SignupComponent } from './signup/signup.component';
import { SignupService } from './services/signup.service';
=======
import { AddRecipeComponent } from './components/add-recipe/add-recipe.component';
import { RecipeService } from './services/recipe.service';
// import { SignupService } from './services/signup.service';
import { SignupComponent } from './SignupView/signup/signup.component';
import { ChefService } from './services/chef.service';
import { LoginComponent } from './LoginView/login/login.component';
>>>>>>> origin/main

@NgModule({
  declarations: [
    AppComponent,
<<<<<<< HEAD
    RecipeEditComponent,
    RecipeDeleteComponent,
    SignupComponent
=======
    AddRecipeComponent,
    SignupComponent,
    LoginComponent
>>>>>>> origin/main
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [ChefService],
  bootstrap: [AppComponent]
})
export class AppModule { }
