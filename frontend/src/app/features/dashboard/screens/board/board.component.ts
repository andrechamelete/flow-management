import { Component, OnInit } from '@angular/core';
import { StageComponent } from './stage/stage.component';
import { CommonModule } from '@angular/common';
import { Stage } from '../../../../models/stage';
import { BoardService } from '../../../../service/board.service';
import { SessionService } from '../../../../service/session.service';
import { Card } from '../../../../models/card';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StageCreateComponent } from '../../stage-create/stage-create.component';
import { ClassOfService } from '../../../../models/ClassOfService';

@Component({
  selector: 'app-board',
  imports: [StageComponent, CommonModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent implements OnInit {

  constructor(private boardService: BoardService, private sessionService: SessionService, private modalService: NgbModal) { }

  stageList: Stage[] = [];
  cardList: Card[] = [];
  classesOfServiceList: ClassOfService[] = [];
  selectedCompany: String | null = null;
  flowId: String | null = null;

  ngOnInit(): void {
    this.sessionService.companyChanges$.subscribe(companyId => {
      this.selectedCompany = companyId;
      this.stageList = [];
      this.flowId = null;
      this.tryLoadBoard();
    });

    this.sessionService.flowChanges$.subscribe(flow => {
      this.flowId = flow;
      this.tryLoadBoard();
    });

  }    

  private tryLoadBoard() {
    if (this.selectedCompany && this.flowId) {
      console.log(this.stageList)
      this.boardService.loadBoard(Number(this.selectedCompany), Number(this.flowId)).subscribe(board => {
        this.stageList = board.stages.sort((a, b) => a.position - b.position);
        console.log('Stages loaded by boardcomp:', this.stageList);
        this.cardList = board.cards.sort((a, b) => a.position - b.position);
        console.log('Cards loaded by boardcomp:', this.cardList);
        this.classesOfServiceList = board.serviceClasses.sort((a, b) => a.id - b.id);
        console.log('Classes of service loaded by boardcomp:', this.classesOfServiceList);
        this.sessionService.setClassesOfService(board.serviceClasses);
      });
    }
  }

  launchCreateStage() {
    console.log("launchCreateStage");
    const modalRef = this.modalService.open(StageCreateComponent);
    
    modalRef.result.then((newStage: Stage) => {
      if (newStage) {
        this.stageList.push(newStage);
      }
    },
    (dismissed) => {
      console.log('Modal dismissed', dismissed);
      }
    );
  }

  onCardMoved(event: any) {
    if (event.type === 'same-stage') {
      // Movimento dentro da mesma stage - atualizar posições de todos os cards da stage
      this.updateSameStagePositions(event.stageId, event.cards);
    } else if (event.type === 'between-stages') {
      // Movimento entre stages - atualizar posições em ambas as stages
      this.updateBetweenStagesPositions(
        event.sourceStageId, 
        event.targetStageId, 
        event.sourceCards, 
        event.targetCards,
        event.movedCard
      );
    }
  }

  private updateSameStagePositions(stageId: number, reorderedCards: Card[]) {
    // Atualizar as posições no array principal
    reorderedCards.forEach((card, index) => {
      const cardIndex = this.cardList.findIndex(c => c.id === card.id);
      if (cardIndex !== -1) {
        this.cardList[cardIndex].position = index;
      }
    });

    // Aqui você pode chamar a API para persistir as mudanças
    console.log('Cards reordenados na stage:', stageId, reorderedCards.map(c => ({id: c.id, position: c.position})));
  }

  private updateBetweenStagesPositions(
    sourceStageId: number, 
    targetStageId: number, 
    sourceCards: Card[], 
    targetCards: Card[],
    movedCard: Card
  ) {
    // Atualizar posições dos cards na stage de origem
    sourceCards.forEach((card, index) => {
      const cardIndex = this.cardList.findIndex(c => c.id === card.id);
      if (cardIndex !== -1) {
        this.cardList[cardIndex].position = index;
      }
    });

    // Atualizar posições dos cards na stage de destino
    targetCards.forEach((card, index) => {
      const cardIndex = this.cardList.findIndex(c => c.id === card.id);
      if (cardIndex !== -1) {
        this.cardList[cardIndex].position = index;
        // Se for o card movido, atualizar também o stage
        if (card.id === movedCard.id) {
          this.cardList[cardIndex].stage = this.stageList.find(s => s.id === targetStageId)!;
        }
      }
    });

    console.log('Card movido entre stages:', {
      movedCard: movedCard.id,
      from: sourceStageId,
      to: targetStageId,
      newPosition: movedCard.position
    });
  }

  get connectedDropListsIds(): string[] {
    return this.stageList.map(stage => 'stage-' + stage.id);
  }
}