import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../models/company';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private apiUrl = 'http://localhost:8080/companies';

  constructor(private http: HttpClient) { }

  getMyCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.apiUrl);    
  }
  
  createCompany(company: Company): Observable<Company> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-type': 'applications/json'
      })
    };
    return this.http.post<Company>(this.apiUrl, company, httpOptions);
  }
}
