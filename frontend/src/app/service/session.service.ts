import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private token: string | null = null;
  private company: string | null = null;
  private flow: string | null = null;

  constructor() { 
    this.token = localStorage.getItem('token');
    this.company = localStorage.getItem('company');
    this.flow = localStorage.getItem('flow');
  }

  getToken(): string | null {
    return this.token;
  }

  setToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  clearToken(): void {
    this.token = null;
    localStorage.removeItem('token');
  }
  
  getCompany(): string | null {
    return this.company;
  }

  setCompany(company: string): void {
    this.company = company;
    localStorage.setItem('company', company);
  }

  getFlow(): string | null {
    return this.flow;
  }

  setFlow(flow: string): void {
    this.flow = flow;
    localStorage.setItem('flow', flow);
  }

  clearSession() {
    this.token = null;
    this.company = null;
    this.flow = null;
    localStorage.clear();
  }
}
