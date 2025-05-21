import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';

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
        component: DashboardComponent
    }
];

export default routeConfig;
export const appRoutes: Routes = [];
