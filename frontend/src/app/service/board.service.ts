import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Stage } from '../models/stage';
import { Card } from '../models/card';
import { HttpClient } from '@angular/common/http';
import { BoardData } from '../models/boardData';
import { ClassOfService } from '../models/ClassOfService';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  private stagesSubject = new BehaviorSubject<Stage[]>([]);
  private cardsSubject = new BehaviorSubject<Card[]>([]);
  private classOfServiceSubject = new BehaviorSubject<ClassOfService[]>([]);

  stages$ = this.stagesSubject.asObservable();
  cards$ = this.cardsSubject.asObservable();
  classOfService$ = this.classOfServiceSubject.asObservable();

  constructor(private http: HttpClient) { }

  loadBoard(companyId: number, flowId: number): Observable<BoardData> {
    return this.http.get<{ stages: Stage[], cards: Card[], serviceClasses: ClassOfService[] }>(`http://localhost:8080/board?company=${companyId}&flow=${flowId}`)
      .pipe(tap(response => {
        this.stagesSubject.next(response.stages);
        this.cardsSubject.next(response.cards);
        this.classOfServiceSubject.next(response.serviceClasses);
      }))
  }
}
