import { Component } from '@angular/core';

@Component({
  selector: 'app-failed-ticket-page',
  templateUrl: './failed-ticket-page.component.html',
  styleUrl: './failed-ticket-page.component.css'
})
export class FailedTicketPageComponent {
  user:any
  ngOnInit(): void {
    this.getUserFromSessionStorage();
  }
  getUserFromSessionStorage(): void {
    const userJson = sessionStorage.getItem('user');
    if (userJson) {
      try {
        this.user = JSON.parse(userJson);
        console.log(this.user);
      } catch (error) {
        console.error('Error parsing session storage data', error);
      }
    } else {
      console.log('No user data found in session storage');
    }
    
    // console.log(this.user.email);
  }
}
