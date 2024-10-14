import { Component, inject } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../service/auth.service';

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
    RouterLinkActive
  ]
})
export class SidenavComponent {
  readonly authService = inject(AuthService);
}
