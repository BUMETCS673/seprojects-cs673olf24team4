import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { AdminLoginDialogComponent } from '../admin-login-dialog/admin-login-dialog.component';
import { AuthService } from '../service/auth.service';
import { ActivatedRoute } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { GroupService } from '../service/group.service';
import { ToastrService } from 'ngx-toastr';
import { Group } from '../model/group.model';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Team } from '../model/team.model';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'home-page',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatExpansionModule,
    MatListModule,
    ReactiveFormsModule,
    CommonModule,
    MatDividerModule
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss'
})
export class HomePageComponent {

  readonly dialog = inject(MatDialog);
  readonly authService = inject(AuthService);

  groups: Group[] = [];
  teams: Team[] = []
  selectedGroup = new FormControl();

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['error'] === 'notAdmin') {
        console.error('You must log in as admin to access that page.');
      }
    });
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id.length > 0) {
      this.selectedGroup.setValue(id);
    } else {
      this.loadAllGroups();
    }
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

  loadAllGroups() {
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

  onGroupSelect(): void {
    if (this.selectedGroup.value) {
      // Fetch teams for the selected group
      this.groupService.getTeamsByGroupId(this.selectedGroup.value).subscribe(teams => {
        this.teams = teams;
      });
    }
  }

}
