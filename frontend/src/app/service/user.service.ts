import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/User';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(private http: HttpClient) { }

  public userLogin(user: User): Observable<User> {
    console.warn(user);
    return this.http.post<User>('http://localhost:8080/api/user/login', user);
  }
  public userRegister(user: User): Observable<User> {
    console.warn(user);
    return this.http.post<User>('http://localhost:8080/api/user/register', user);
  }
}
