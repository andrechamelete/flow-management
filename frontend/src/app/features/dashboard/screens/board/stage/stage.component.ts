import { Component, Input, OnInit } from '@angular/core';
import { Stage } from '../../../../../models/stage';
import { BoardService } from '../../../../../service/board.service';
import { Card } from '../../../../../models/card';
import { CardComponent } from '../card/card.component';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-stage',
  imports: [CommonModule, CardComponent],
  templateUrl: './stage.component.html',
  styleUrl: './stage.component.scss'
})
export class StageComponent {

  @Input() stage!: Stage;
  //@Input() cards!: Card;
  @Input() cardList!: Card[];


  constructor(private boardService: BoardService) { }

/*  getCardsByStage(stageId: number): Card[] {
    console.log('cards by stagecomp:', this.cardList)
    return this.cardList.filter(card => card.stage.id === stageId);
  } */

  cardsInStage: Card[] = [];

  ngOnChanges(): void {
    this.cardsInStage = this.cardList.filter(card => card.stage.id === this.stage.id);
}
}


