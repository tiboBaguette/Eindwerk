import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ListPostComponent } from './components/list-post/list-post.component';
import {RegisterFormComponent} from './components/register-form/register-form.component';
import {HomepageComponent} from './components/homepage/homepage.component';
import {LoginFormComponent} from './components/login-form/login-form.component';
import {CreatePostComponent} from './components/create-post/create-post.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import { ListCategoriesComponent } from './components/list-categories/list-categories.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import { CreateCommentComponent } from './components/create-comment/create-comment.component';
import {CreateCategoryComponent} from './components/create-category/create-category.component';
import { EditPostComponent } from './components/edit-post/edit-post.component';
import { AboutPageComponent } from './components/about-page/about-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomepageComponent,
    LoginFormComponent,
    RegisterFormComponent,
    CreatePostComponent,
    CreateCategoryComponent,
    ListPostComponent,
    ListCategoriesComponent,
    PostDetailsComponent,
    CreateCommentComponent,
    EditPostComponent,
    AboutPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
