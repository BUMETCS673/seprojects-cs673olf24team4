import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'user-details',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatSelectModule
  ],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.scss'
})
export class UserDetailsComponent {
  
  userForm: FormGroup;

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private userService: UserService) {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      degree: ['', Validators.required],
      concentration: [''],
    });
  }

  ngOnInit(): void {
    // check if in edit mode
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id.length > 0) {
      this.loadUserData(Number(id));
    }
  }

  submit(): void {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      console.log('User Data:', userData);
    }
  }

  loadUserData(id: number) {
    this.userService.getUserById(id).subscribe(user => {
      if (user) {
        this.userForm.patchValue(user);
      }
    });
  }

}
