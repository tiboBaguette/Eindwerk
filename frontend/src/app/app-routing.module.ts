import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import {ListPostComponent} from './components/list-post/list-post.component';
import {CreatePostComponent} from './components/create-post/create-post.component';
import {CreateCategoryComponent} from './components/create-category/create-category.component';

const routes: Routes = [
  {path: 'login-form', component: LoginFormComponent},
  {path: 'register-form', component: RegisterFormComponent},
  {path: 'create-post', component: CreatePostComponent},
  {path: 'create-category', component: CreateCategoryComponent},
  {path: 'list-posts', component: ListPostComponent},
  {path: '', component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
