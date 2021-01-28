import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import {CreatePostComponent} from './create-post/create-post.component';

const routes: Routes = [
  {path: 'login-form', component: LoginFormComponent},
  {path: 'register-form', component: RegisterFormComponent},
  {path: 'create-post', component: CreatePostComponent},
  {path: '', component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
