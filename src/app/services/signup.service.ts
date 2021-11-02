import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http'
import { SendChef, Chef } from '../models/Chef';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private url = "http://localhost:8080/BackEnd/chef";

  constructor(private httpClient: HttpClient) { }

  addChef(chef: SendChef): Observable<HttpResponse<Chef>> {
    return this.httpClient.post<Chef>(this.url, chef, {observe : 'response', headers: {'password': chef.password}});
  }
}
