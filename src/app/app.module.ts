import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
<<<<<<< HEAD
import { AddRecipeComponent } from './components/add-recipe/add-recipe.component';
import { RecipeService } from './services/recipe.service';
import { SignupComponent } from './signup/signup.component';
import { SignupService } from './services/signup.service';
=======
import { SignupComponent } from './SignupView/signup/signup.component';
import { ChefService } from './services/chef.service';
import { LoginComponent } from './LoginView/login/login.component';
>>>>>>> 0e53f76e869620afeafbee04b7aada669abcec6f

@NgModule({
  declarations: [
    AppComponent,
<<<<<<< HEAD
    AddRecipeComponent,
    SignupComponent
=======
    SignupComponent,
    LoginComponent
>>>>>>> 0e53f76e869620afeafbee04b7aada669abcec6f
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
<<<<<<< HEAD
  providers: [RecipeService, SignupService],
=======
  providers: [ChefService],
>>>>>>> 0e53f76e869620afeafbee04b7aada669abcec6f
  bootstrap: [AppComponent]
})
export class AppModule { }
