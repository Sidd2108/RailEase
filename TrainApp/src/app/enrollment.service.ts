import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './user';
import { UserLogin } from './user-login';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  _registerUrl = `http://${window.location.host}:5001/v1/users/register`;
  _loginUrl = `http://${window.location.host}:5001/v1/users/login`;
  _updateUrl = `http://${window.location.host}:5001/v1/users/updateProfile`
  constructor(private __http:HttpClient) { }

  loginUser(user:UserLogin){
    return this.__http.post<any>(this._loginUrl,user);
  }

  enroll(user:User){
    return this.__http.post<any>(this._registerUrl,user);
  }

  updateUser(user:User){
    return this.__http.put<any>(this._updateUrl,user);
  }

}
