import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Stage } from '../../../../../models/stage';
import { BoardService } from '../../../../../service/board.service';
import { Card } from '../../../../../models/card';
import { CardComponent } from '../card/card.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../../../../../service/session.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CardCreateComponent } from '../../../card-create/card-create.component'; 
import { DragDropModule, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

@Component({
  standalone: true,
  selector: 'app-stage',
  imports: [CommonModule, CardComponent, FormsModule, DragDropModule],
  templateUrl: './stage.component.html',
  styleUrl: './stage.component.scss'
})
export class StageComponent {

  @Input() stage!: Stage;
  @Input() cardListing!: Card[];
  @Input() connectedDropListsIds: string[] = [];
  @Output() cardMoved = new EventEmitter<{
    type: 'same-stage' | 'between-stages',
    stageId?: number,
    sourceStageId?: number,
    targetStageId?: number,
    cards?: Card[],
    sourceCards?: Card[],
    targetCards?: Card[],
    movedCard: Card,
    newPosition: number
  }>();


  constructor(
    private boardService: BoardService, 
    private http: HttpClient, 
    private sessionService: SessionService,
    private modalService: NgbModal) { }

  drop(event: CdkDragDrop<Card[]>) {
    const card = event.item.data || event.previousContainer.data[event.previousIndex];
    
    if (event.previousContainer === event.container) {
      // Movimento dentro da mesma stage
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      
      this.moveWithinStage(card.id, event.currentIndex)
      // Emitir evento para o componente pai com todos os cards afetados
      this.cardMoved.emit({
        type: 'same-stage',
        stageId: this.stage.id,
        cards: event.container.data,
        movedCard: card,
        newPosition: event.currentIndex
      });
    } else {
      // Movimento entre stages diferentes
      const sourceStageId = event.previousContainer.id.replace('stage-', '');
      
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
      
      // Atualizar o stage do card
      card.stage = this.stage;

      this.moveToAnotherStage(card.id, this.stage.id, event.currentIndex)
      
      // Emitir evento para o componente pai
      this.cardMoved.emit({
        type: 'between-stages',
        sourceStageId: parseInt(sourceStageId),
        targetStageId: this.stage.id,
        sourceCards: event.previousContainer.data,
        targetCards: event.container.data,
        movedCard: card,
        newPosition: event.currentIndex
      });
    }
  }

  private moveWithinStage(cardId: number, newPosition: number) {
    const data = { newPosition };
    this.http.patch<Card>(`http://localhost:8080/board/card/reorder/${cardId}`, data)
      .subscribe({
        next: (response) => console.log("Card reordered successfully", response),
        error: (error) => console.error("Error reordering card", error)
    })
  }

  private moveToAnotherStage(cardId: number, targetStageId: number, newPosition: number) {
    const data = { targetStageId, newPosition };
    this.http.patch<Card>(`http://localhost:8080/board/card/move/${cardId}`, data)
      .subscribe({
        next: (response) => console.log("Card moved successfully", response),
        error: (error) => console.error("Error moving card", error)
      })
  }

  trackByCardId(index: number, card: Card) {
    return card.id;
  }

  cardsInStage: Card[] = [];

  ngOnChanges(): void {
    this.cardsInStage = this.cardListing
      .filter(card => card.stage.id === this.stage.id)
      .sort((a, b) => a.position - b.position);
  }

  isEditing: boolean = false;

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  save() {
    const data = {
      name: this.stage.name,
      wipLimit: this.stage.wipLimit
    }
    this.isEditing = false;
    return this.http.patch<Stage>(`http://localhost:8080/board/stage/${this.stage.id}`, data).subscribe({
      next: (response) => {
        console.log("Stage updated successfully", response);
      },
      error: (error) => {
        console.log("Error updating stage", error);
      }
    })
  }

  launchCreateCard() {
    console.log("Opening modal to create card at stage: ", this.stage.name);
    const modalRef = this.modalService.open(CardCreateComponent);
    modalRef.componentInstance.stageId = this.stage.id;
    modalRef.result.then((newCard: Card) => {
      if (newCard) {
        this.cardListing.push(newCard);
      }
    },
    (reason) => {
      console.log('Modal closed with reason:', reason);
    });
  }

  onCardUpdated(updatedCard: Card) {
    const index = this.cardListing.findIndex(card => card.id === updatedCard.id);
    if (index !== -1) {
      this.cardListing[index] = {...updatedCard}
    }
  }
}


