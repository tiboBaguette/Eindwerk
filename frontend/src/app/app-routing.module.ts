import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent} from './homepage/homepage.component';
import { LoginFormComponent} from './login-form/login-form.component';

const routes: Routes = [
  {path: 'login-form', component: LoginFormComponent},
  {path: '', component: HomepageComponent},



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
