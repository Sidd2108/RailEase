import { Component } from '@angular/core';
import { UserLogin } from '../user-login';
import { EnrollmentService } from '../enrollment.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: any;
  

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
