import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RecipeEditComponent } from './recipe/recipe-edit/recipe-edit.component';
import { RecipeDeleteComponent } from './recipe/recipe-delete/recipe-delete.component';

@NgModule({
  declarations: [
    AppComponent,
    RecipeEditComponent,
    RecipeDeleteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
