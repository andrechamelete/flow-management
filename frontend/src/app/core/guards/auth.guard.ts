import { CanActivate, Router, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = localStorage.getItem('token');
    if(token) {
      return true;
    } else {
      return this.router.createUrlTree(['/home']);
    }
  }
}

/*
export const authGuard: CanActivateFn = (route, state) => {
  return true;
}; */
