import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { User } from '../model/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../service/auth.service';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

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

  constructor(
    private router: Router, 
    private userService: UserService,
    private authService: AuthService, 
    private toastr: ToastrService) {
    
  }

  ngOnInit(): void {
    this.loadAllUsers();
  }

  editUser(user: User) {
    this.router.navigate(['/user-details', user.id]);
  }

  deleteUser(user: User) {
    const adminUsername = this.authService.getUsername();
    const adminPassword = this.authService.getPassword();
    if (adminUsername && adminPassword) {
      const adminData: AdminData = {
        username: adminUsername,
        password: adminPassword
      }
      this.userService.deleteUser(user.id, adminData).subscribe({
        complete: () => { 
          this.toastr.success('User deleted successfully!'); 
          this.loadAllUsers();
        },
        error: () => { 
          this.toastr.error('Error deleting user'); 
        }
      });
    } else {
      console.error('Admin credentials not set'); 
    }
    
  }

  loadAllUsers() {
    const adminUsername = this.authService.getUsername();
    const adminPassword = this.authService.getPassword();
    if (adminUsername && adminPassword) {
      const adminData: AdminData = {
        username: adminUsername,
        password: adminPassword
      }
      this.userService.getAllUsers(adminData).subscribe({
        next: (data) => {
          this.users = data;
        },
        complete: () => { 
          // if needed
        },
        error: () => { 
          this.toastr.error('Unexpected error in getting all users'); 
        }
      });
    } else {
      console.error('Admin credentials not set'); 
    }
    
  }

}
