import { Component } from '@angular/core';
import { User } from '../user';
import { EnrollmentService } from '../enrollment.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  userModel = new User("","","",0,"");

  
  constructor(private _enrollmentService: EnrollmentService,private router: Router) {}

  submit(){
    this._enrollmentService.enroll(this.userModel)
    .subscribe(
      data=>{
        console.log('Success!',data);
        sessionStorage.setItem('user', JSON.stringify(data)); // Save user data to sessionStorage
        this.router.navigate(['']); 
      },
      error => console.error('Error!',error)
      
    )
  }
}
