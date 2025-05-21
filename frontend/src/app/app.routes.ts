import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';

const routeConfig: Routes = [
    { 
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    { 
        path: 'home',
        component: HomeComponent 
    }
];

export default routeConfig;
export const appRoutes: Routes = [];
