import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chef, SendChef } from '../models/Chef';

@Injectable({
  providedIn: 'root'
})
export class ChefService {

  private url = "http://ec2-3-140-238-125.us-east-2.compute.amazonaws.com:8090/Potluck/chef";

  constructor(private httpClient: HttpClient) { }

  addChef(chef: SendChef): Observable<HttpResponse<Chef>> {
    return this.httpClient.post<Chef>(this.url, chef, {observe : 'response', headers: {'password': chef.password}});
  }

  login(username: string, password: string): Observable<HttpResponse<Chef>> {
    return this.httpClient.get<Chef>(this.url+`/${username}`, {observe : 'response', headers: {'password': password}});
  }
}
