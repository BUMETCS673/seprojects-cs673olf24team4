import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { User } from '../model/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'view-users',
  standalone: true,
  imports: [
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    MatMenuModule
  ],
  templateUrl: './view-users.component.html',
  styleUrl: './view-users.component.scss'
})
export class ViewUsersComponent {

  users: User[] = [];

  displayedColumns: string[] = ['id', 'name', 'email', 'actions'];

  constructor(private router: Router, private userService: UserService) {
    
  }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe(data => this.users = data);
  }

  editUser(user: User) {
    this.router.navigate(['/user-details', user.id]);
  }

  deleteUser(user: User) {

  }

}
