import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {

  constructor( private http: HttpClient) { }

  login(username: string, password: string) {
    const credentials = { username: username, password: password };
    return this.http.post('login', credentials);
  }

}
