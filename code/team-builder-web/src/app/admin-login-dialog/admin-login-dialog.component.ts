import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { AuthService } from '../service/auth.service';
import { ToastrService } from 'ngx-toastr';

export interface AdminData {
  username: string;
  password: string;
}

@Component({
  selector: 'admin-login-dialog',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatCardModule
  ],
  templateUrl: './admin-login-dialog.component.html',
  styleUrl: './admin-login-dialog.component.scss'
})
export class AdminLoginDialogComponent {
  username: string = '';
  password: string = '';
  readonly dialogRef = inject(MatDialogRef<AdminLoginDialogComponent>);

  constructor(private authService: AuthService, private toastr: ToastrService) {}

  login() {
    const adminData: AdminData = {username: this.username, password: this.password};
    this.authService.authenticate(adminData).subscribe({
      complete: () => { 
        this.toastr.success('Logged in as Admin successfully!'); 
        this.authService.setCredentials(this.username, this.password);
        this.dialogRef.close();
      },
      error: () => { 
        this.toastr.error('Invalid admin credentials. Please try again.'); 
        this.authService.clearCredentials();
      }
    });
    
  }
  
}
