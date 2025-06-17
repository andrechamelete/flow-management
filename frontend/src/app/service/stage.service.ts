import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SessionService } from './session.service';
import { Stage } from '../models/stage';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StageService {

  private apiUrl = 'http://localhost:8080/board/stage';

  constructor(private http: HttpClient, private sessionService: SessionService) { }

  editingStage(stage: Stage): Observable<Stage> {
  const httpHeader = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.sessionService.getToken()}`
    }
  };
  return this.http.put<Stage>(`${this.apiUrl}/${stage.id}`, stage, httpHeader)
  }

}


