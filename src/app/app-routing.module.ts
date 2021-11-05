import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KitchenComponent } from './KitchenView/kitchen/kitchen.component';
import { LoginComponent } from './LoginView/login/login.component';
import { MarketComponent } from './MarketView/market/market.component';
import { SignupComponent } from './SignupView/signup/signup.component';

const routes: Routes = [
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'kitchen', component: KitchenComponent },
  { path: 'market', component: MarketComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
