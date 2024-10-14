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

@Component({
  selector: 'app-form-teams',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatExpansionModule,
    MatListModule,
    CommonModule
  ],
  templateUrl: './form-teams.component.html',
  styleUrl: './form-teams.component.scss'
})
export class FormTeamsComponent {

  groups: Group[] = [];

  constructor(
    private groupService: GroupService, 
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
    
  }
}
