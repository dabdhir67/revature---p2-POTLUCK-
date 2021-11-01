import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Chef } from '../models/Chef';
import { SignupService } from '../services/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private signupService: SignupService) { }

  private chef: Chef = {
    id: 0,
    username: 'user',
    passkey: 'pass',
    firstName: 'one',
    lastName: 'first',
    email: 'email@email.com'
  }

  ngOnInit(): void {
  }

  buildAndAddChef() {
    this.signupService.addChef().toPromise()
      .then(response => console.log(response))
      .catch(() => alert("Duplicate Username!"));
  }

}
