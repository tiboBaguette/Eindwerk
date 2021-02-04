import { Component, OnInit } from '@angular/core';
import {User} from '../../model/User';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {FormBuilder} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  user: User;
  isError: boolean | undefined;

  registerForm = this.formBuilder.group({
    username: '',
    email: '',
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

  register(): void {
    this.userService.userRegister(this.registerForm.value).subscribe(
        response => this.user = response,
        error => this.handleError(error),
        () => this.registerUser()
    );
  }

  handleError(error: HttpErrorResponse): void {
    if (error.status === 409) {
      console.warn('401: Conflict');
      this.isError = true;
    }
  }

  registerUser(): void {
    this.userService.setActiveUser(this.user);
    this.registerForm.reset();
    this.router.navigateByUrl('list-posts');
  }
}
