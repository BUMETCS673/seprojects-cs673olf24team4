import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormTeamsComponent } from './form-teams.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideToastr } from 'ngx-toastr';

describe('FormTeamsComponent', () => {
  let component: FormTeamsComponent;
  let fixture: ComponentFixture<FormTeamsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormTeamsComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideToastr()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
