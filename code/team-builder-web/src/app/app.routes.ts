import { Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';
import { ViewUsersComponent } from './view-users/view-users.component';
import { HomePageComponent } from './home-page/home-page.component';
import { AuthGuard } from './guards/auth.guard';
import { CreateEditGroupComponent } from './create-edit-group/create-edit-group.component';

export const routes: Routes = [
    {path: 'home', component: HomePageComponent},
    {path: 'user-details', component: UserDetailsComponent},
    {path: 'user-details/:id', component: UserDetailsComponent},
    {path: 'view-users', component: ViewUsersComponent, canActivate: [AuthGuard]},
    {path: 'create-edit-group', component: CreateEditGroupComponent, canActivate: [AuthGuard]},
];
