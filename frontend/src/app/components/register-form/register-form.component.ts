import { Component, OnInit } from '@angular/core';
import {User} from '../../model/User';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  user: User;

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
        res => this.user = res,
        err => console.log('HTTP Error', err),
        () => console.log('HTTP request completed.')
    );

    console.warn(this.user);
  }
}
