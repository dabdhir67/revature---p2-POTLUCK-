import { Component, OnInit } from '@angular/core';
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
  errText: string = 'There was an unknown error.';
  badRequest: boolean = false;

  ngOnInit(): void {
    sessionStorage.removeItem('token');
  }

  login() {
    this.loginService.login(this.username, this.password).subscribe(
      result => {
        if (result.status === 200) {
          const token = result.headers.get('Authorization');
          if (token) {
            sessionStorage.setItem('token', token);
            window.location.href = '/kitchen';
          }
        }
      },
      err => {
        if (err.status === 403 || err.status === 404) {
          this.errText = 'Username or Password is Incorrect!';
          this.badRequest = true;
          document.getElementById('err-div')!.style.display = "block";
        } else if (err.status === 400) {
          this.errText = 'All Fields must be filled in.'
          this.badRequest = true;
          document.getElementById('err-div')!.style.display = "block";
        } else {
          document.getElementById('err-div')!.style.display = "block";
        }
      }
    );
  }

}
