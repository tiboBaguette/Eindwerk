import { Component, OnInit } from '@angular/core';
import {User} from '../model/User';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {
  constructor() {  }

  ngOnInit(): void {
  }

}
