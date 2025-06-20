import { Injectable, Injector } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ClassOfService } from '../models/ClassOfService';
import { BoardService } from './board.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private companyIdSubject = new BehaviorSubject<String | null>(this.getCompany())
  private flowIdSubject = new BehaviorSubject<String | null>(this.getFlow())
  private classesOfServiceSubject = new BehaviorSubject<ClassOfService[]>([]);

  companyChanges$ = this.companyIdSubject.asObservable();
  flowChanges$ = this.flowIdSubject.asObservable();
  classesOfServiceChanges$ = this.classesOfServiceSubject.asObservable();

  private readonly TOKEN_KEY = 'token';
  private readonly COMPANY_KEY = 'company';
  private readonly FLOW_KEY = 'flow';

  private token: string | null = null;
  private company: string | null = null;
  private flow: string | null = null;

  constructor(private injector: Injector) { 
    this.token = localStorage.getItem(this.TOKEN_KEY);
    this.company = localStorage.getItem(this.COMPANY_KEY);
    this.flow = localStorage.getItem(this.FLOW_KEY);

    this.companyIdSubject.next(this.company);
    this.flowIdSubject.next(this.flow);
  }

  getToken(): string | null {
    if(this.token === null) {
      this.company = localStorage.getItem(this.TOKEN_KEY)
    }
    return this.token;
  }

  setToken(token: string): void {
    const previousToken = this.token;
    this.token = token;
    localStorage.setItem(this.TOKEN_KEY, token);

    if (previousToken && previousToken !== token) {
      this.clearCompanyAndFlow();
    }
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
    this.flowIdSubject.next(value);
  }

  setClassesOfService(list: ClassOfService[]) {
    this.classesOfServiceSubject.next(list);
    console.log('sessionService classes of service: ', this.classesOfServiceSubject.value);
  }

  clearSession() {
    this.company = null;
    this.flow = null;
    localStorage.removeItem(this.FLOW_KEY);
    localStorage.removeItem(this.COMPANY_KEY);
    this.clearToken();

    const boardService = this.injector.get(BoardService);
    boardService.clearBoard();
  }

  clearCompanyAndFlow() {
    this.company = null;
    this.flow = null;
    localStorage.removeItem(this.FLOW_KEY);
    localStorage.removeItem(this.COMPANY_KEY);
    this.companyIdSubject.next(null);
    this.flowIdSubject.next(null);
  }
}
