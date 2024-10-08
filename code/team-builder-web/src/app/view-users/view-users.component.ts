import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { User } from '../model/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

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

  displayedColumns: string[] = ['name', 'email', 'role', 'answers', 'actions'];

  constructor(private router: Router, private userService: UserService, private toastr: ToastrService) {
    
  }

  ngOnInit(): void {
    this.loadAllUsers();
  }

  editUser(user: User) {
    this.router.navigate(['/user-details', user.id]);
  }

  deleteUser(user: User) {
    this.userService.deleteUser(user.id).subscribe({
      complete: () => { 
        this.toastr.success('User deleted successfully!'); 
        this.loadAllUsers();
      },
      error: () => { 
        this.toastr.error('Error deleting user'); 
      }
    });
  }

  loadAllUsers() {
    this.userService.getAllUsers().subscribe(data => this.users = data);
  }

}
