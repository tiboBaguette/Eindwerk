import { Component, OnInit } from '@angular/core';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  user: User;

  loginForm = this.formBuilder.group({
    username: '',
    password: '',
  });

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  register() {
    this.userService.userLogin(this.loginForm.value).subscribe();
  }
}
