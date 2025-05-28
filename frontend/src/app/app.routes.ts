import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { AuthGuard } from './core/guards/auth.guard';
import { NoAuthGuard } from './core/guards/no-auth.guard';

const routeConfig: Routes = [
    { 
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    { 
        path: 'home',
        component: HomeComponent,
        title: 'Home',
        canActivate: [NoAuthGuard]
    },
    {
        path: 'dashboard',
        component: DashboardComponent,
        title: 'Dashboard',
        canActivate: [AuthGuard]
    },
    {
        path: '**', redirectTo: 'home'
    }
];

export default routeConfig;
export const appRoutes: Routes = [];
