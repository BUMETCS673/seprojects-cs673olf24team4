import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEditGroupComponent } from './create-edit-group.component';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideToastr } from 'ngx-toastr';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('CreateEditGroupComponent', () => {
  let component: CreateEditGroupComponent;
  let fixture: ComponentFixture<CreateEditGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CreateEditGroupComponent,
        NoopAnimationsModule
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { params: of({ id: '1' }) }, // mock params
        },
        provideHttpClient(),
        provideHttpClientTesting(),
        provideToastr()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEditGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
