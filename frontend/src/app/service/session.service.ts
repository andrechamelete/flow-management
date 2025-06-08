import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private companyIdSubject = new BehaviorSubject<String | null>(this.getCompany())

  companyChanges$ = this.companyIdSubject.asObservable();

  private readonly TOKEN_KEY = 'token';
  private readonly COMPANY_KEY = 'company';
  private readonly FLOW_KEY = 'flow';

  private token: string | null = null;
  private company: string | null = null;
  private flow: string | null = null;

  constructor() { 
    this.token = localStorage.getItem(this.TOKEN_KEY);
    this.company = localStorage.getItem(this.COMPANY_KEY);
    this.flow = localStorage.getItem(this.FLOW_KEY);
  }

  getToken(): string | null {
    if(this.token === null) {
      this.company = localStorage.getItem(this.TOKEN_KEY)
    }
    return this.token;
  }

  setToken(token: string): void {
    this.token = token;
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  clearToken(): void {
    this.token = null;
    localStorage.removeItem(this.TOKEN_KEY);
  }
  
  getCompany(): string | null {
    if(this.company === null) {
      this.company = localStorage.getItem(this.COMPANY_KEY);
    }
    return this.company;
  }

  setCompany(company: string | number): void {
    const value = String(company);
    this.company = value;
    localStorage.setItem(this.COMPANY_KEY, value);
    this.companyIdSubject.next(value);
  }

  getFlow(): string | null {
    if(this.flow === null) {
      this.flow = localStorage.getItem(this.FLOW_KEY)
    }
    return this.flow;
  }

  setFlow(flow: string | number): void {
    const value = String(flow);
    this.flow = value;
    localStorage.setItem(this.FLOW_KEY, value);
  }

  clearSession() {
    this.company = null;
    this.flow = null;
    localStorage.removeItem(this.FLOW_KEY);
    localStorage.removeItem(this.COMPANY_KEY);
    this.clearToken();
  }
}
