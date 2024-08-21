import { Component } from '@angular/core';
import { EnrollmentService } from '../enrollment.service';
import { UserLogin } from '../user-login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  userModel = new UserLogin("","");
  
  constructor(private _enrollmentService: EnrollmentService,private router: Router) {}

  submit(){
    this._enrollmentService.loginUser(this.userModel)
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
