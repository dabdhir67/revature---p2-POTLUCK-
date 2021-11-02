import { Component, OnInit } from '@angular/core';
import { SignupService } from '../../services/signup.service';
import { HttpResponse } from '@angular/common/http';

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

  addChef() {
    const chef = {
      username : this.username,
      password : this.password,
      firstName : this.firstName,
      lastName : this.lastName,
      email : this.email
    }
    this.signupService.addChef(chef).toPromise()
      .then(response => {
        if (response.status === 201) {
          const token = response.headers.get('Authorization');
          if (token) sessionStorage.setItem('token', token);
        }
      })
      .catch(response => {
        if (response.status === 400) {
          alert('All fields must be filled!');
        } else if (response.status === 500) {
          alert('Duplicate Key!')
        }
      });
  }

}
