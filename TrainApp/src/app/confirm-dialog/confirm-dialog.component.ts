import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TicketBookingService } from '../ticket-booking.service';
import { BookTicketParam } from '../bookTicketParam';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css'] // Changed from styleUrl to styleUrls
})
export class ConfirmDialogComponent {
  fareData: any;
  user: any;
  bookTicket = new BookTicketParam(0,"");
  constructor(
    private _ticketService: TicketBookingService,
    private router: Router,
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.fareData = data.fareData; // Access fare data
    console.log(this.fareData.routeId);
    
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
    
  }
  onCancel(): void {
    this.dialogRef.close(false);
  }

  
  onConfirm(): void {
    this.getUserFromSessionStorage();
    this.bookTicket = new BookTicketParam(this.fareData.routeId,this.user.email);
    console.log(this.bookTicket);
    
    this._ticketService.bookTicket(this.bookTicket)
      .subscribe(
        data => {
          console.log('Success!',data);
          // sessionStorage.setItem('user', JSON.stringify(data)); // Save user data to sessionStorage
          sessionStorage.setItem('ticket', JSON.stringify(data)); // Save user data to sessionStorage
        this.router.navigate(['ticket']); 
        this.dialogRef.close(true);
        },
        error => console.error('Error!', error)
      );

}

}