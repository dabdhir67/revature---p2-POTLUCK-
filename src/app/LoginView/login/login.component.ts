import { Component, OnInit } from '@angular/core';
import { SendChef } from 'src/app/models/Chef';
import { ChefService } from 'src/app/services/chef.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: ChefService) { }

  username: string = '';
  password: string = '';

  ngOnInit(): void {
    sessionStorage.removeItem('token');
  }

  login() {
    this.loginService.login(this.username, this.password).subscribe(
      result => {
        const token = result.headers.get('Authorization');
        if (token) {
          sessionStorage.setItem('token', token);
          window.location.href = '/kitchen';
        }
      }
    );
  }

}
