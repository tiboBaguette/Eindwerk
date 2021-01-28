import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/User';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private readonly userLoginUrl = 'http://localhost:8080/user/login';
  private readonly userRegisterUrl = 'http://localhost:8080/user/register';
  constructor(private http: HttpClient) { }

  public userLogin(user: User): Observable<User> {
    return this.http.post<User>(this.userLoginUrl, user);
  }
  public userRegister(user: User): Observable<User> {
    return this.http.post<User>(this.userRegisterUrl, user);
  }
}
