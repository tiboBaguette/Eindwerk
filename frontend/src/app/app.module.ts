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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomepageComponent,
    LoginFormComponent,
    RegisterFormComponent,
    CreatePostComponent,
    ListPostComponent,
    ListCategoriesComponent
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
