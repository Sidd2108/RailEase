import { Component } from '@angular/core';
import { User } from '../user';
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  userModel = new User("","","",0,"");

  
  constructor(private _enrollmentService: EnrollmentService,private router: Router,private authService: AuthService) {}

  submit(){
    this._enrollmentService.enroll(this.userModel)
    .subscribe(
      data=>{
        console.log('Success!',data);
        sessionStorage.setItem('user', JSON.stringify(data)); // Save user data to sessionStorage
        this.router.navigate(['home']); 
        this.authService.login('some-token');
      },
      error => console.error('Error!',error)
      
    )
  }
}
