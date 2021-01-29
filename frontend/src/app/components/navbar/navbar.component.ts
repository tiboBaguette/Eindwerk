import {AfterContentChecked, Component, OnInit} from '@angular/core';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, AfterContentChecked {
  user: User | undefined;
  isLoggedIn: boolean | undefined;

  constructor(
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = false;
  }

  ngAfterContentChecked(): void {
    this.user = this.userService.user;
    this.isLoggedIn = this.user !== undefined;
  }

  logout(): void {
    this.isLoggedIn = false;
    this.userService.setActiveUser(undefined);
  }
}
