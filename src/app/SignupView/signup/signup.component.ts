import { Component, OnInit } from '@angular/core';
import { ChefService } from '../../services/chef.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private chefService: ChefService) { }

  username: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  errText: string = 'An unknown error has occured.';
  badRequest: boolean = false;

  ngOnInit(): void {
  }

  clearForm() {
    this.username = '';
    this.password = '';
    this.firstName = '';
    this.lastName = '';
    this.email = '';
  }

  addChef() {
    const chef = {
      username : this.username,
      password : this.password,
      firstName : this.firstName,
      lastName : this.lastName,
      email : this.email
    }
    this.chefService.addChef(chef).subscribe(
      response => {
        if (response.status === 201) {
          const token = response.headers.get('Authorization');
          if (token) sessionStorage.setItem('token', token);
          this.clearForm();
          window.location.href = '/kitchen';
        }
      },
      err => {
        if (err.status === 500) {
          this.errText = 'Username is already taken!';
          this.badRequest = true;
          document.getElementById('err-div')!.style.display = "block";
          this.username = '';
        } else if (err.status === 400) {
          this.errText = 'All fields must be filled in!';
          this.badRequest = true;
          document.getElementById('err-div')!.style.display = "block";
        }else {
          this.clearForm();
          document.getElementById('err-div')!.style.display = "block";
        }
      }
    );
  }

}
