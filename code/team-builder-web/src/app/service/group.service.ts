import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Group } from '../model/group.model';
import { Team } from '../model/team.model';
import { AuthService } from './auth.service';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private apiUrl = 'https://seprojects-cs673olf24team4.onrender.com/api/groups';

  constructor(private http: HttpClient) {}

  createGroup(group: Group): Observable<Group> {
    return this.http.post<Group>(this.apiUrl, group);
  }

  getAllGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiUrl);
  }

  getGroupById(id: number): Observable<Group> {
    return this.http.get<Group>(`${this.apiUrl}/${id}`);
  }

  assignTeams(groupId: number, numberOfTeams: number, adminData: AdminData): Observable<Team[]> {
    return this.http.post<Team[]>(`${this.apiUrl}/${groupId}/assign-teams`, {}, {
      params: new HttpParams()
        .set('adminUsername', adminData.username)
        .set('adminPassword', adminData.password)
        .set('numberOfTeams', numberOfTeams)
    });
  }

  getTeamsByGroupId(groupId: number): Observable<Team[]> {
    return this.http.get<Team[]>(`${this.apiUrl}/${groupId}/teams`);
  }
  
}
