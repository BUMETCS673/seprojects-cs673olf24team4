import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SidenavComponent } from './sidenav.component';
import { By } from '@angular/platform-browser';

describe('SidenavComponent', () => {
  let component: SidenavComponent;
  let fixture: ComponentFixture<SidenavComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [NoopAnimationsModule]
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

  it('should have three navigation items - Home, Add Student, View/Edit Students', () => {
    const navListItems = fixture.debugElement.queryAll(By.css('.sidenav-list-item'));

    expect(navListItems.length).toBe(3);
    expect(navListItems[0].query(By.css('span:nth-child(2)')).nativeElement.textContent.trim()).toBe('Home');
    expect(navListItems[1].query(By.css('span:nth-child(2)')).nativeElement.textContent.trim()).toBe('Add Student');
    expect(navListItems[2].query(By.css('span:nth-child(2)')).nativeElement.textContent.trim()).toBe('View/Edit Students');
  });

});
