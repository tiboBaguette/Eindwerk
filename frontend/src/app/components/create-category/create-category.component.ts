import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CategoryService} from '../../service/category.service';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.css']
})
export class CreateCategoryComponent implements OnInit {
  isError: boolean | undefined;
  createCategoryForm = this.formBuilder.group({
    name: '',
  });

  constructor(
    private categoryService: CategoryService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.userService.setActiveUser(this.userService.user);
  }

  ngOnInit(): void {
  }

  createCategory(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.categoryService.createCategory(this.createCategoryForm.value).subscribe();
      this.createCategoryForm.reset();
      this.router.navigateByUrl('list-categories');
    }
  }
}
