import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { TicketPageComponent } from './ticket-page/ticket-page.component';
import { FailedTicketPageComponent } from './failed-ticket-page/failed-ticket-page.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { EditBalanceComponent } from './edit-balance/edit-balance.component';
import { AuthGuard } from './auth.guard';

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
    path: 'home',
    canActivate: [AuthGuard] 
  },
  {
    path: '', 
    redirectTo:"/home",
    pathMatch:"full"
  }
  ,
  {
    component: ProfileComponent,
    path: 'profile',
    canActivate: [AuthGuard] 
  },
  {
    component: TicketPageComponent,
    path: 'ticket',
    canActivate: [AuthGuard] 
  },
  {
    component: FailedTicketPageComponent,
    path: 'failedTicket',
    canActivate: [AuthGuard] 
  },
  {
    component: EditUserComponent,
    path: 'editUser',
    canActivate: [AuthGuard] 
  },
  {
    component: EditBalanceComponent,
    path: 'editBalance',
    canActivate: [AuthGuard] 
  },
  {
    path:"**",
    redirectTo:"home"
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
