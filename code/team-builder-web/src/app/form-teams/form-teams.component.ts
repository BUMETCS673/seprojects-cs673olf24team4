import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { Group } from '../model/group.model';
import { GroupService } from '../service/group.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../service/auth.service';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

@Component({
  selector: 'app-form-teams',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatExpansionModule,
    MatListModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  templateUrl: './form-teams.component.html',
  styleUrl: './form-teams.component.scss'
})
export class FormTeamsComponent {

  groups: Group[] = [];
  teamCount: FormControl = new FormControl(); 

  constructor(
    private groupService: GroupService, 
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit() {
    this.groupService.getAllGroups().subscribe({
      next: (data) => {
        this.groups = data;
      },
      complete: () => { 
        // if needed
      },
      error: () => { 
        this.toastr.error('Error fetching all groups'); 
      }
    });
  }

  editGroup(group: Group) {
    this.router.navigate(['/create-edit-group', group.id]);
  }

  formTeams(group: Group) {
    const adminUsername = this.authService.getUsername();
    const adminPassword = this.authService.getPassword();
    if (adminUsername && adminPassword) {
      const adminData: AdminData = {
        username: adminUsername,
        password: adminPassword
      }
      this.groupService.assignTeams(group.id, this.teamCount.value, adminData).subscribe({
        complete: () => { 
          this.toastr.success('Teams formed successfully! Select Group ID to view teams'); 
          this.router.navigate(['home', group.id]);
        },
        error: () => { 
          this.toastr.error('Error forming teams'); 
        }
      });
    } else {
      console.error('Admin credentials not set');
    }
  
    
  }
}
