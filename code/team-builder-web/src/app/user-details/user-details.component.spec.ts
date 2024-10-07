import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDetailsComponent } from './user-details.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { By } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { provideToastr } from 'ngx-toastr';

describe('UserDetailsComponent', () => {
  let component: UserDetailsComponent;
  let fixture: ComponentFixture<UserDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        UserDetailsComponent,
        NoopAnimationsModule,
        RouterModule.forRoot([]),
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule
      ],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideToastr(),
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create user-details', () => {
    expect(component).toBeTruthy();
  });

  it('should disable the save button when the form is invalid', () => {
    const saveButton = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    expect(component.userForm.invalid).toBeTruthy();
    fixture.detectChanges();
    expect(saveButton.disabled).toBeTruthy();
  });

  it('should enable the save button when the form is valid', () => {
    component.userForm.controls['name'].setValue('Johnny Bravo');
    component.userForm.controls['email'].setValue('johnny.bravo@cn.com');
    component.userForm.controls['degree'].setValue('Master of Fine Arts');
    component.userForm.controls['preferredRole'].setValue('Team Leader');
    fixture.detectChanges();

    const saveButton = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    expect(component.userForm.valid).toBeTruthy();
    expect(saveButton.disabled).toBeFalsy();
  });
  
});
