import { Component, Input, OnInit } from '@angular/core';
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

@Component({
  standalone: true,
  selector: 'app-stage',
  imports: [CommonModule, CardComponent, FormsModule],
  templateUrl: './stage.component.html',
  styleUrl: './stage.component.scss'
})
export class StageComponent {

  @Input() stage!: Stage;
  @Input() cardListing!: Card[];


  constructor(
    private boardService: BoardService, 
    private http: HttpClient, 
    private sessionService: SessionService,
    private modalService: NgbModal) { }

/*  getCardsByStage(stageId: number): Card[] {
    console.log('cards by stagecomp:', this.cardList)
    return this.cardList.filter(card => card.stage.id === stageId);
  } */

  cardsInStage: Card[] = [];

  ngOnChanges(): void {
    this.cardsInStage = this.cardListing.filter(card => card.stage.id === this.stage.id);
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
    return this.http.patch<Stage>(`http://localhost:8080/board/stage/${this.stage.id}`, data, {
      headers: {
        Authorization: 'Bearer' + this.sessionService.getToken()
      }
    }).subscribe({
      next: (response) => {
        console.log("Stage updated successfully", response);
      },
      error: (error) => {
        console.log("Error updating stage", error);
      }
    })
  }

  launchCreateCard() {
    const modalRef = this.modalService.open(CardCreateComponent);
    modalRef.componentInstance.stageId = this.stage.id
  }
}


