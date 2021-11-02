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
=======
import { SignupComponent } from './signup/signup.component';
import { SignupService } from './services/signup.service';
>>>>>>> origin/main

@NgModule({
  declarations: [
    AppComponent,
<<<<<<< HEAD
    RecipeEditComponent,
    RecipeDeleteComponent
=======
    SignupComponent
>>>>>>> origin/main
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [SignupService],
  bootstrap: [AppComponent]
})
export class AppModule { }
