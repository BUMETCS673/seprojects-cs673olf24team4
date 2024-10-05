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
import { MatIconModule } from '@angular/material/icon';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { map, Observable, startWith } from 'rxjs';
import { CommonModule } from '@angular/common';
import { ALL_SKILLS } from '../constants/skills.constants';
import { User } from '../model/user.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'user-details',
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
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.scss'
})
export class UserDetailsComponent {
  
  userForm: FormGroup;

  separatorKeysCodes: number[] = [ENTER, COMMA];
  allSkills: string[] = ALL_SKILLS;
  filteredSkills: Observable<string[]>;

  predefinedRoles: string[] = [
    'Team Leader',
    'Requirements Leader',
    'Design and Implementation Leader',
    'Quality Assurance Leader',
    'Security Leader'
  ];

  constructor(
    private fb: FormBuilder, 
    private route: ActivatedRoute, 
    private userService: UserService,
    private toastr: ToastrService
  ) {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      degree: [''],
      concentration: [''],
      selectedSkills: [[], Validators.required],
      skillInput: [''],
      preferredRole: ['', Validators.required]
    });
    this.filteredSkills = this.skillInput!.valueChanges.pipe(
      startWith(''),
      map((language: string | null) => language ? this._filter(language) : this.allSkills.slice())
    );
  }

  ngOnInit(): void {
    // check if in edit mode
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id.length > 0) {
      this.loadUserData(id);
    }
  }

  submit(): void {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      console.log('User Data:', userData);
      const user = new User({
        name: this.name?.value,
        email: this.email?.value,
        role: this.preferredRole?.value,
        answers: this.selectedSkills?.value
      });

      // check if in edit mode
      const id = this.route.snapshot.paramMap.get('id');
      if (id && id.length > 0) {
        this.userService.updateUser(id, user).subscribe({
          complete: () => { 
            this.toastr.success('User updated successfully!'); 
          },
          error: () => { 
            this.toastr.error('Error updating user'); 
          }
        });
      } else {
        this.userService.createUser(user).subscribe({
          complete: () => { 
            this.toastr.success('User saved successfully!'); 
          },
          error: () => { 
            this.toastr.error('Error saving user'); 
          }
        });
      }
    }
  }

  loadUserData(id: string) {
    this.userService.getUserById(id).subscribe(user => {
      if (user) {
        this.userForm.patchValue({
          name: user.name,
          email: user.email,
          selectedSkills: user.answers,
          preferredRole: user.role
        });
      }
    });
  }

  get name() {
    return this.userForm.get('name');
  }

  get email() {
    return this.userForm.get('email');
  }

  get degree() {
    return this.userForm.get('degree');
  }

  get concentration() {
    return this.userForm.get('concentration');
  }

  get selectedSkills() {
    return this.userForm.get('selectedSkills');
  }

  get skillInput() {
    return this.userForm.get('skillInput');
  }

  get preferredRole() {
    return this.userForm.get('preferredRole');
  }

  addSkill(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value && this.selectedSkills?.value.indexOf(value) === -1) {
      this.selectedSkills.value.push(value);
    }
    event.chipInput?.clear();
    this.skillInput?.setValue(null);
  }

  removeSkill(language: string): void {
    const index = this.selectedSkills?.value.indexOf(language);
    if (index >= 0) {
      this.selectedSkills?.value.splice(index, 1);
    }
  }

  selectSkill(event: MatAutocompleteSelectedEvent): void {
    const value = event.option.viewValue;
    if (this.selectedSkills?.value.indexOf(value) === -1) {
      this.selectedSkills.value.push(value);
    }
    this.skillInput?.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allSkills.filter(language => language.toLowerCase().includes(filterValue));
  }

}
