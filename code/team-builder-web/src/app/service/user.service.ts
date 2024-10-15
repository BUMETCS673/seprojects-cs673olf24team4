import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'https://seprojects-cs673olf24team4.onrender.com/api/users';

  constructor(private http: HttpClient) { }

  getAllUsers(adminData: AdminData): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl, { 
      params: new HttpParams()
      .set('adminUsername', adminData.username)
      .set('adminPassword', adminData.password) 
    });
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}`, user);
  }

  deleteUser(id: string, adminData: AdminData): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { 
      params: new HttpParams()
      .set('adminUsername', adminData.username)
      .set('adminPassword', adminData.password) 
    });
  }
  
  updateUser(id: string, user: User, adminData: AdminData): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, user, { 
      params: new HttpParams()
      .set('adminUsername', adminData.username)
      .set('adminPassword', adminData.password) 
    });
  }

  submitAnswers(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/submit-answers`, user);
  }
  
  assignUserToGroup(userId: string, groupId: number): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/${userId}/assign-group/${groupId}`, {});
  }
  
}
