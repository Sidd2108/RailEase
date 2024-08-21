import { Component } from '@angular/core';

@Component({
  selector: 'app-ticket-page',
  templateUrl: './ticket-page.component.html',
  styleUrl: './ticket-page.component.css'
})
export class TicketPageComponent {
  ticket: any;
  paymentTime: any;
  paymentDate: any


  ngOnInit(): void {
    this.getUserFromSessionStorage();
  }
  getUserFromSessionStorage(): void {

    const userJson = sessionStorage.getItem('ticket');
    if (userJson) {
      try {
        this.ticket = JSON.parse(userJson);
        console.log(this.ticket);
      } catch (error) {
        console.error('Error parsing session storage data', error);
      }
    } else {
      console.log('No user data found in session storage');
    }

    const paymentTime= this.ticket.paymentTime;

    // Split the string at 'T'
    const [datePart, timePart] = paymentTime.split('T');

    // The date part
    const dateString: string = datePart;              // "2024-08-20"

    // The time part (excluding milliseconds)
    const timeString: string = timePart.split('.')[0]; // "10:25:05"
    this.paymentDate = dateString;
    this.paymentTime = timeString;

    // console.log(this.user.email);
  }
}
