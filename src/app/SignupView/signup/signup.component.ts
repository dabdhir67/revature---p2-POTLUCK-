import { Component, OnInit } from '@angular/core';
import { EmailValidator } from '@angular/forms';
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

  ngOnInit(): void {
  }

  clearForm() {
    this.username = '';
    this.password = '';
    this.firstName = '';
    this.lastName = '';
    this.email = '';
  }

  invalidFields(): boolean {
    let pattern = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    if (this.username === '') {
      document.getElementById('username')?.classList.add('is-invalid');
    }
    if (this.password === '') {
      document.getElementById('password')?.classList.add('is-invalid');
    }
    if (this.firstName === '') {
      document.getElementById('firstName')?.classList.add('is-invalid');
    }
    if (this.lastName === '') {
      document.getElementById('lastName')?.classList.add('is-invalid');
    }
    if (this.email === '') {
      document.getElementById('email')?.classList.add('is-invalid');
    }
    if (!pattern.test(this.email)) {
      document.getElementById('email')?.classList.add('is-invalid');
    }
    if (this.username === '' || this.password === '' || this.firstName === '' ||
        this.lastName === '' || this.email === '' || !pattern.test(this.email)) {
      this.errText = 'All Fields must be filled in!';
      if (!pattern.test(this.email)) this.errText += "\nPlease enter a valid email!";
      document.getElementById('err-div')!.style.display = "block";
      return true;
    }
    return false;
  }

  addChef() {
    if (this.invalidFields()) return;
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
          document.getElementById('err-div')!.style.display = "block";
          this.username = '';
        } else if (err.status === 400) {
          this.errText = 'All fields must be filled in!';
          document.getElementById('err-div')!.style.display = "block";
        }else {
          this.clearForm();
          document.getElementById('err-div')!.style.display = "block";
        }
      }
    );
  }

}
