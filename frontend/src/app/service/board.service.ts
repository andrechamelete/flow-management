import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Stage } from '../models/stage';
import { Card } from '../models/card';
import { HttpClient } from '@angular/common/http';
import { BoardData } from '../models/boardData';
import { ClassOfService } from '../models/ClassOfService';
import { SessionService } from './session.service';
import { CardType } from '../models/CardType';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  private stagesSubject = new BehaviorSubject<Stage[]>([]);
  private cardsSubject = new BehaviorSubject<Card[]>([]);
  private classOfServiceSubject = new BehaviorSubject<ClassOfService[]>([]);
  private CardTypeSubject = new BehaviorSubject<CardType[]>([]);

  stages$ = this.stagesSubject.asObservable();
  cards$ = this.cardsSubject.asObservable();
  classOfService$ = this.classOfServiceSubject.asObservable();
  cardType$ = this.CardTypeSubject.asObservable();

  constructor(private http: HttpClient, private sessionService: SessionService) {
    this.sessionService.companyChanges$.subscribe(company => this.tryLoadBoard());
    this.sessionService.flowChanges$.subscribe(flow => this.tryLoadBoard());
  }

  tryLoadBoard() {
    const company = this.sessionService.getCompany();
    const flow = this.sessionService.getFlow();

    if (company && flow) {
      this.loadBoard(+company, +flow).subscribe();
    }
  }

  loadBoard(companyId: number, flowId: number): Observable<BoardData> {
    return this.http.get<BoardData>(`http://localhost:8080/board?company=${companyId}&flow=${flowId}`)
      .pipe(tap(response => {
        this.stagesSubject.next(response.stages.sort((a,b ) => a.position - b.position));
        this.cardsSubject.next(response.cards.sort((a, b) => a.position - b.position));
        this.classOfServiceSubject.next(response.serviceClasses);
        this.CardTypeSubject.next(response.cardTypes);

      }))
  }

  updateCardLocal(updatedCard: Card) {
    const cards = this.cardsSubject.value.map(card => card.id === updatedCard.id ? updatedCard : card);
    this.cardsSubject.next(cards);
  }

  updateStageLocal(UpdatedStage: Stage) {
    const stages = this.stagesSubject.value.map(stage => stage.id === UpdatedStage.id ? {...stage, ...UpdatedStage} : stage);
    this.stagesSubject.next(stages);
  }

  addCardLocal(newCard: Card) {
    const cards = [...this.cardsSubject.getValue(), newCard];
    this.cardsSubject.next(cards);
  }

  clearBoard(): void {
    this.stagesSubject.next([]);
    this.cardsSubject.next([]);
    this.classOfServiceSubject.next([]);
    this.CardTypeSubject.next([]);
  }
}
