import { Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';
import { ViewUsersComponent } from './view-users/view-users.component';

export const routes: Routes = [
    {path: 'user-details', component: UserDetailsComponent},
    {path: 'user-details/:id', component: UserDetailsComponent},
    {path: 'view-users', component: ViewUsersComponent}
];
