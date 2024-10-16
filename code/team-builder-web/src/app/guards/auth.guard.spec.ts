import { TestBed } from '@angular/core/testing';
import { AuthGuard } from './auth.guard';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../service/auth.service';

describe('AuthGuard', () => {
  let authGuard: AuthGuard;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterModule.forRoot([]),
      ],
      providers: [
        AuthGuard,
        {
          provide: AuthService,
          useValue: {
            isLoggedIn: jasmine.createSpy().and.returnValue(false)
          }
        }
      ]
    });

    authGuard = TestBed.inject(AuthGuard);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
  });

  it('should be created', () => {
    expect(authGuard).toBeTruthy();
  });

  it('should allow activation if the user is logged in', () => {
    (authService.isLoggedIn as jasmine.Spy).and.returnValue(true);
    expect(authGuard.canActivate()).toBe(true);
  });

  it('should not allow activation if the user is not logged in', () => {
    (authService.isLoggedIn as jasmine.Spy).and.returnValue(false);
    expect(authGuard.canActivate()).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['/home'], { queryParams: { error: 'notAdmin' } });
  });
});
