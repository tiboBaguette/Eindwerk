import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { RegisterFormComponent } from './components/register-form/register-form.component';
import {ListPostComponent} from './components/list-post/list-post.component';

const routes: Routes = [
  {path: 'login-form', component: LoginFormComponent},
  {path: 'register-form', component: RegisterFormComponent},
  {path: 'list-posts', component: ListPostComponent},
  {path: '', component: HomepageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
