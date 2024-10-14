import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { AdminLoginDialogComponent } from '../admin-login-dialog/admin-login-dialog.component';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'home-page',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {

  readonly dialog = inject(MatDialog);
  readonly authService = inject(AuthService);

  constructor() {}

  openDialog(): void {
    const dialogRef = this.dialog.open(AdminLoginDialogComponent, {
      width: '400px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      // if needed 
    });
  }

}
