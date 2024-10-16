import { Component, inject } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatDialog } from '@angular/material/dialog';
import { AdminLoginDialogComponent } from '../admin-login-dialog/admin-login-dialog.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'sidenav',
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.scss',
  standalone: true,
  imports: [
    MatSidenavModule,
    MatListModule,
    RouterOutlet, 
    RouterLink, 
    RouterLinkActive,
    MatSlideToggleModule,
    FormsModule
  ]
})
export class SidenavComponent {
  readonly authService = inject(AuthService);

  adminToggle: boolean = false;

  constructor(public dialog: MatDialog) {
    this.adminToggle = this.authService.isLoggedIn();
  }

  toggleAdminLogin(): void {
    if (this.adminToggle) {
      this.openAdminLoginDialog();
    } else {
      this.authService.clearCredentials(); // logout
    }
  }

  openAdminLoginDialog(): void {
    const dialogRef = this.dialog.open(AdminLoginDialogComponent, {
      width: '400px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (this.authService.isLoggedIn()) {
        this.adminToggle = true;
      } else {
        this.adminToggle = false;
      }
    });
  }

}
