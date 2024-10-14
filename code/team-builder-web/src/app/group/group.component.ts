import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute } from '@angular/router';
import { User } from '../model/user.model';
import { UserService } from '../service/user.service';
import { AuthService } from '../service/auth.service';
import { AdminData } from '../admin-login-dialog/admin-login-dialog.component';
import { ToastrService } from 'ngx-toastr';
import { Group } from '../model/group.model';
import { GroupService } from '../service/group.service';

@Component({
  selector: 'app-group',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatSelectModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule,
    CommonModule
  ],
  templateUrl: './group.component.html',
  styleUrl: './group.component.scss'
})
export class GroupComponent {

  groupForm: FormGroup;
  allUsers: User[] = [];

  constructor(
    private fb: FormBuilder, 
    private route: ActivatedRoute,
    private userService: UserService,
    private groupService: GroupService,
    private authService: AuthService,
    private toastr: ToastrService
  ) {
    this.groupForm = this.fb.group({
      name: ['', Validators.required],
      selectedUsers: [[], Validators.required]
    });
  }

  get name() {
    return this.groupForm.get('name');
  }

  get selectedUsers() {
    return this.groupForm.get('selectedUsers');
  }

  ngOnInit(): void {
    const adminUsername = this.authService.getUsername();
    const adminPassword = this.authService.getPassword();
    if (adminUsername && adminPassword) {
      const adminData: AdminData = {
        username: adminUsername,
        password: adminPassword
      }
      this.userService.getAllUsers(adminData).subscribe({
        next: (data) => {
          this.allUsers = data;
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

  submit() {
    if (this.groupForm.valid) {
      const groupData = this.groupForm.value;
      console.log('Group Data:', groupData);
      const group = new Group({
        name: this.name?.value,
        users: this.selectedUsers?.value
      });

      this.groupService.createGroup(group).subscribe({
        complete: () => {
          this.toastr.success('Group created successfully!');
        },
        error: () => {
          this.toastr.error('Error creating group');
        }
      });
    }
  }


}
