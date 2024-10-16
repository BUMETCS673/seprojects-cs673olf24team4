import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SidenavComponent } from './sidenav.component';
import { By } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { UserDetailsComponent } from '../user-details/user-details.component';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggle } from '@angular/material/slide-toggle';

describe('SidenavComponent', () => {
  let component: SidenavComponent;
  let fixture: ComponentFixture<SidenavComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        RouterModule.forRoot([
          {path: 'user-details', component: UserDetailsComponent}
        ]),
        FormsModule,
        MatSidenavModule
      ],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create sidenav', () => {
    expect(component).toBeTruthy();
  });

  it('should have two navigation items as a non Admin - Home, Add User', () => {
    const navListItems = fixture.debugElement.queryAll(By.css('.sidenav-list-item'));
    expect(navListItems.length).toBe(2);
    expect(navListItems[0].query(By.css('span:nth-child(2)')).nativeElement.textContent.trim()).toBe('Home');
    expect(navListItems[1].query(By.css('span:nth-child(2)')).nativeElement.textContent.trim()).toBe('Add User');
  });

  it('should display the admin login toggle with the correct default label', () => {
    const toggle = fixture.debugElement.query(By.directive(MatSlideToggle));
    expect(toggle).toBeTruthy();
    expect(toggle.nativeElement.textContent.trim()).toContain('Login as Admin');
  });

  it('should call toggleAdminLogin when the slide toggle is changed', () => {
    spyOn(component, 'toggleAdminLogin');
    const toggle = fixture.debugElement.query(By.directive(MatSlideToggle));
    toggle.triggerEventHandler('change', { checked: true });
    fixture.detectChanges();
    expect(component.toggleAdminLogin).toHaveBeenCalled();
  });

});
