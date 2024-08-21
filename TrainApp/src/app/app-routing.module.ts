import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { TicketPageComponent } from './ticket-page/ticket-page.component';
import { FailedTicketPageComponent } from './failed-ticket-page/failed-ticket-page.component';

const routes: Routes = [
  {
    component: SignupComponent,
    path: 'signup'
  },
  {
    component: LoginComponent,
    path: 'login'
  },
  {
    component: HomeComponent,
    path: ''
  },
  {
    component: ProfileComponent,
    path: 'profile'
  },
  {
    component:TicketPageComponent,
    path:'ticket'
  },
  {
    component:FailedTicketPageComponent,
    path:'failedTicket'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
