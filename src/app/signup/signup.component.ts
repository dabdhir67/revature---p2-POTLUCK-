import { Component, OnInit } from '@angular/core';
import { Chef } from '../models/Chef';
import { SignupService } from '../services/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private signupService: SignupService) { }

  username: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  email: string = '';

  ngOnInit(): void {
  }

  buildChef() {

  }

  // addChef() {
  //   this.signupService.addChef(this.chef).toPromise()
  //     .then(response => function() {
  //       if (response.status == 400) {
  //         console.log(response);
  //       }
  //     })
  //     .catch(response => {
  //       if (response.status == 400) {
  //         alert("Must fill all fields!");
  //       } else if (response.status == 500) {
  //         alert("Duplicate Username!");
  //       }
  //     });
  // }

}
