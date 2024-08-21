import { Component } from '@angular/core';
import { TicketBookingService } from '../ticket-booking.service';
import { Router } from '@angular/router';
import { SourceDest } from '../sourceDest';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'] // Changed from styleUrl to styleUrls
})
export class HomeComponent {
  public source: string = '';
  public destination: string = '';
  sourceDest = new SourceDest(this.source, this.destination);
  fareData: any; // Variable to hold fare data

  constructor(
    private _ticketService: TicketBookingService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  getSource(item: any) {
    this.source = item.target.value;
  }

  getDestination(item: any) {
    this.destination = item.target.value;
  }

  getTheFare() {
    this.sourceDest = new SourceDest(this.source, this.destination);
    console.log(this.sourceDest);
    
    this._ticketService.getFare(new SourceDest(this.source, this.destination))
      .subscribe(
        data => {
          console.log('Success!', data);
          this.fareData = data; // Store fare data
          this.openConfirmDialog(); // Open dialog after setting fare data
        },
        error => console.error('Error!', error)
      );
  }

  openConfirmDialog(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: { fareData: this.fareData } // Pass fare data to dialog
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Optionally handle result if needed
      }
    });
  }
}
