import { Component, OnInit } from '@angular/core';
import {Category} from '../../model/Category';
import {CategoryService} from '../../service/category.service';

@Component({
  selector: 'app-list-categories',
  templateUrl: './list-categories.component.html',
  styleUrls: ['./list-categories.component.css']
})
export class ListCategoriesComponent implements OnInit {
  categories: Category[] = [];

  // mock data voor te testen
  // category: Category = new Category();
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategories().toPromise().then((categoryResponse) => this.categories = categoryResponse);
    // this.category.name = 'Wall Street Bets';
    // this.categories.push(this.category);
  }

}
