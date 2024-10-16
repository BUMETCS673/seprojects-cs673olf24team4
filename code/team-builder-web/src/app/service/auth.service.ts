import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'https://seprojects-cs673olf24team4.onrender.com/api/admin/authenticate';

  private adminUsername: string | null = null;
  private adminPassword: string | null = null;

  constructor(private http: HttpClient) {}

  authenticate(adminData: AdminData): Observable<string> {
    return this.http.post(this.apiUrl, adminData, {responseType: 'text'});
  }

  setCredentials(username: string, password: string): void {
    this.adminUsername = username;
    this.adminPassword = password;
  }

  clearCredentials(): void {
    this.adminUsername = null;
    this.adminPassword = null;
  }

  getUsername(): string | null {
    return this.adminUsername;
  }

  getPassword(): string | null {
    return this.adminPassword;
  }

  isLoggedIn(): boolean {
    return this.adminUsername !== null && this.adminPassword !== null;
  }

}
