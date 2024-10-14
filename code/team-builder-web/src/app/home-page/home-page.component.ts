import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { AdminLoginDialogComponent } from '../admin-login-dialog/admin-login-dialog.component';
import { AuthService } from '../service/auth.service';
import { ActivatedRoute } from '@angular/router';

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

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['error'] === 'notAdmin') {
        console.error('You must log in as admin to access that page.');
      }
    });
  }

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
