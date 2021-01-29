import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/User';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  public user: User | undefined;

  constructor(private http: HttpClient) { }

  public userLogin(user: User): Observable<User> {
    return this.http.post<User>('http://localhost:8080/api/user/login', user);
  }
  public userRegister(user: User): Observable<User> {
    return this.http.post<User>('http://localhost:8080/api/user/register', user);
  }


  public setActiveUser(user: User | undefined): void {
    this.user = user;
  }
}
