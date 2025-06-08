import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flow } from '../models/flow';
import { SessionService } from './session.service'; 

@Injectable({
  providedIn: 'root'
})
export class FlowService {

  private apiUrl = 'http://localhost:8080/flows';

  constructor(private http: HttpClient, private sessionService: SessionService) {
    this.sessionService.getCompany();

   }

  getFlows(): Observable<Flow[]> {
    const company = Number(this.sessionService.getCompany());
    const params = new HttpParams().set("company", company);
    const debugUrl = `${this.apiUrl}?${params.toString()}`;
    console.log("Chamando endpoint:", debugUrl);
    return this.http.get<Flow[]>(this.apiUrl, { params });
  }

  creatingFlow(flow: Flow): Observable<Flow> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-type': 'applications/json'
      })
    };
    return this.http.post<Flow>(this.apiUrl, flow, httpOptions)
  }
}
