import { Component } from '@angular/core';
import { User } from '../user';
import { Router } from '@angular/router';
import { EnrollmentService } from '../enrollment.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent {
  user: any;
  
  userModel = new User("","","",0,"");
  constructor(private _enrollmentService: EnrollmentService,private router: Router) {}

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
    
  this.userModel = new User(this.user.username,this.user.email,this.user.phone,this.user.balance,this.user.password);

    // console.log(this.user.email);
  }
  
  submit(){
    console.log(this.userModel);
    
    this._enrollmentService.updateUser(this.userModel)
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
