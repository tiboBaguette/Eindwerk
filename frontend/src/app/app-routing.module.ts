import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import {ListPostComponent} from './components/list-post/list-post.component';
import {CreatePostComponent} from './components/create-post/create-post.component';
import {CreateCategoryComponent} from './components/create-category/create-category.component';
import {ListCategoriesComponent} from './components/list-categories/list-categories.component';
import {PostDetailsComponent} from './components/post-details/post-details.component';
import {CreateCommentComponent} from './components/create-comment/create-comment.component';
import {EditPostComponent} from './components/edit-post/edit-post.component';

const routes: Routes = [
  {path: 'login-form', component: LoginFormComponent},
  {path: 'register-form', component: RegisterFormComponent},
  {path: 'create-post', component: CreatePostComponent},
  {path: 'create-category', component: CreateCategoryComponent},
  {path: 'list-posts', component: ListPostComponent},
  {path: 'list-categories', component: ListCategoriesComponent},
  {path: 'post-details/:id', component: PostDetailsComponent},
  {path: 'create-comment/:id', component: CreateCommentComponent},
  {path: 'edit-post/:id', component: EditPostComponent},
  {path: '', component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
