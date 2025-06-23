import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Card } from '../../../../../models/card';
import { CommonModule } from '@angular/common';
import { CardInfoComponent } from '../../../card-info/card-info.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClassOfService } from '../../../../../models/ClassOfService';
import { SessionService } from '../../../../../service/session.service';
import { CardType } from '../../../../../models/CardType';
import { BoardService } from '../../../../../service/board.service';

@Component({
  standalone: true,
  selector: 'app-card',
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent implements OnInit {
  
  @Input() card!: Card;
  @Output() cardUpdated = new EventEmitter<Card>();
  classesOfService: ClassOfService[] = [];
  cardTypes: CardType[] = [];

  constructor(private modalService: NgbModal, private sessionService: SessionService, private boardService: BoardService) { }

  ngOnInit() {
    this.boardService.classOfService$.subscribe(data => {
      this.classesOfService = data;
    })

    this.boardService.cardType$.subscribe(data => {
      this.cardTypes = data;
    })
  }

  openModalCard() {
    console.log('classe de serviço: ', this.card.classOfService?.id);
    console.log('types: ', this.cardTypes);
    const modalRef = this.modalService.open(CardInfoComponent);
    modalRef.componentInstance.card$ = this.card;
    modalRef.componentInstance.onUpdate = (updatedCard: Card) => {
      Object.assign(this.card, updatedCard);
    }
  }

}
