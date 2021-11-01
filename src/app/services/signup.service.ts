import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'
import { Chef } from '../models/Chef';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private url = "http://ec2-3-140-238-125.us-east-2.compute.amazonaws.com:8090/Potluck/chef";

  constructor(private httpClient: HttpClient) { }

  addChef(chef: Chef): Observable<HttpResponse<Chef>> {
    return this.httpClient.post<Chef>(this.url, chef, {observe : 'response'});
  }
}
