import { Component, OnInit } from '@angular/core';
import {Category} from '../../model/Category';
import {CategoryService} from '../../service/category.service';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-list-categories',
  templateUrl: './list-categories.component.html',
  styleUrls: ['./list-categories.component.css']
})
export class ListCategoriesComponent implements OnInit {
  categories: Category[] = [];
  activeUser: User | undefined;

  constructor(
    private categoryService: CategoryService,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.categoryService.getCategories().toPromise().then((categoryResponse) => this.categories = categoryResponse);
    this.activeUser = this.userService.user;


    setTimeout(() =>
      {
        this.categoryService.getCategories().toPromise().then((categoryResponse) => this.categories = categoryResponse);
      },
      61);
  }

}
