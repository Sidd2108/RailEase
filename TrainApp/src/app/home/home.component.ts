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
  public selectedSource: string = '';
  public selectedDestination: string = '';
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
    this.sourceDest = new SourceDest(this.selectedSource, this.selectedDestination);
    console.log(this.sourceDest);
    
    this._ticketService.getFare(new SourceDest(this.selectedSource, this.selectedDestination))
      .subscribe(
        data => {
          console.log('Success!', data);
          this.fareData = data; // Store fare data
          this.openConfirmDialog(); // Open dialog after setting fare data
        },
        error => {
        console.error('Error!', error)
          
        this.router.navigate(['failedTicket']); 
        }
          
      );
  }

  stations = [
    'Churchgate',
    'Marine Lines',
    'Charni Road',
    'Grant Road',
    'Mumbai Central',
    'Mahalaxmi',
    'Lower Parel',
    'Prabhadevi',
    'Dadar',
    'Matunga Road',
    'Mahim Junction',
    'Bandra',
    'Khar Road',
    'Santacruz',
    'Vile Parle',
    'Andheri',
    'Jogeshwari',
    'Ram Mandir',
    'Goregaon',
    'Malad',
    'Kandivali',
    'Borivali',
    'Dahisar',
    'Mira Road',
    'Bhayandar',
    'Naigaon',
    'Vasai Road',
    'Nalasopara',
    'Virar',
    'Vaitarna',
    'Saphale',
    'Kelva Road',
    'Palghar',
    'Umroli',
    'Boisar',
    'Vangaon',
    'Dahanu Road',
    'Parel',
    'Currey Road',
    'Chinchpokli',
    'Byculla',
    'Sandhurst Road',
    'Masjid Bunder',
    'CST (Chhatrapati Shivaji Maharaj Terminus)',
    'Vidyavihar',
    'Ghatkopar',
    'Vikhroli',
    'Kanjurmarg',
    'Bhandup',
    'Nahur',
    'Mulund',
    'Thane',
    'Kalwa',
    'Mumbra',
    'Diva Junction',
    'Kopar',
    'Dombivli',
    'Thakurli',
    'Kalyan',
    'Vitthalwadi',
    'Ulhasnagar',
    'Ambarnath',
    'Badlapur',
    'Vangani',
    'Shelu',
    'Neral',
    'Bhivpuri Road',
    'Karjat',
    'Kurla',
    'Tilak Nagar',
    'Chembur',
    'Govandi',
    'Mankhurd',
    'Vashi',
    'Sanpada',
    'Juinagar',
    'Nerul',
    'Seawoods-Darave',
    'Belapur CBD',
    'Kharghar',
    'Mansarovar',
    'Khandeshwar',
    'Panvel'
  ];

  openConfirmDialog(): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: { fareData: this.fareData } // Pass fare data to dialog
    });

  }
}
