import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);

  constructor() {
    // Ideally, you would check the user's authentication state from a backend or local storage
    // For demo purposes, we'll assume the user is logged out initially
    this.loggedIn.next(!!localStorage.getItem('userToken')); 
  }

  // Call this method to log in the user
  login(token: string): void {
    localStorage.setItem('userToken', token);
    this.loggedIn.next(true);
  }

  // Call this method to log out the user
  logout(): void {
    localStorage.removeItem('userToken');
    this.loggedIn.next(false);
  }

  // Check if the user is logged in
  isAuthenticated(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }
}
