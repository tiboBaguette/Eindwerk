import { Component, OnInit } from '@angular/core';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';
import {FormBuilder} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  user: User;
  httpErrorResponse: HttpErrorResponse | undefined;
  isError: boolean | undefined;

  loginForm = this.formBuilder.group({
    username: '',
    password: '',
  });

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  login(): void {
    this.userService.userLogin(this.loginForm.value).subscribe(
      response => this.user = response,
      error => this.handleError(error),
      () => this.logInUser()
    );
  }

  handleError(error: HttpErrorResponse): void {
    if (error.status === 401) {
      console.warn('401: Unauthorized');
      this.isError = true;
    }
  }

  logInUser(): void {
    this.userService.setActiveUser(this.user);
    this.loginForm.reset();
    this.router.navigateByUrl('list-posts');
  }
}
