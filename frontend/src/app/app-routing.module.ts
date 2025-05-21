import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { appRoutes } from "./app.routes";

const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: "home",
        loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent)
    },
    {
        path: '**', redirectTo: 'home'
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
    }
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }