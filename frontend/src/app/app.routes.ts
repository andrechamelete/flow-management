import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { AuthGuard } from './core/guards/auth.guard';

const routeConfig: Routes = [
    { 
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    { 
        path: 'home',
        component: HomeComponent 
    },
    {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard]
    }
];

export default routeConfig;
export const appRoutes: Routes = [];
