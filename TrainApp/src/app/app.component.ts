import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TrainApp';

  constructor(private router: Router,private authService: AuthService) { }
  onLogout(): void {
    sessionStorage.clear(); // Clear all data from sessionStorage
    // Additional logout procedures can be added here
    this.authService.logout();
    this.router.navigate(['login']); // Redirect to login page after logout

  }

}