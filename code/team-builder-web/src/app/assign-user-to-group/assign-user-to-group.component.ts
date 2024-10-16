import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { UserService } from '../service/user.service';
import { GroupService } from '../service/group.service';
import { AuthService } from '../service/auth.service';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/user.model';
import { Group } from '../model/group.model';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';

@Component({
  selector: 'app-assign-user-to-group',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule
  ],
  templateUrl: './assign-user-to-group.component.html',
  styleUrls: ['./assign-user-to-group.component.scss']
})
export class AssignUserToGroupComponent implements OnInit {
  assignForm: FormGroup;
  users: User[] = [];
  groups: Group[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private groupService: GroupService,
    private authService: AuthService,
    private toastr: ToastrService
  ) {
    this.assignForm = this.fb.group({
      userId: ['', Validators.required],
      groupId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUsers();
    this.loadGroups();
  }

  loadUsers(): void {
    const adminData: AdminData = {
      username: this.authService.getUsername() || '',
      password: this.authService.getPassword() || ''
    };
    this.userService.getAllUsers(adminData).subscribe({
      next: (users) => this.users = users,
      error: (error) => this.toastr.error('Error loading users')
    });
  }

  loadGroups(): void {
    this.groupService.getAllGroups().subscribe({
      next: (groups) => this.groups = groups,
      error: (error) => this.toastr.error('Error loading groups')
    });
  }

  onSubmit(): void {
    if (this.assignForm.valid) {
      const { userId, groupId } = this.assignForm.value;
      this.userService.assignUserToGroup(userId, groupId).subscribe({
        next: (response) => {
          this.toastr.success('User assigned to group successfully');
          this.assignForm.reset();
        },
        error: (error) => this.toastr.error('Error assigning user to group')
      });
    }
  }
}
