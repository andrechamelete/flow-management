import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Card } from '../../../../../models/card';
import { CommonModule } from '@angular/common';
import { CardInfoComponent } from '../../../card-info/card-info.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  standalone: true,
  selector: 'app-card',
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  
  @Input() card!: Card;
  @Output() cardUpdated = new EventEmitter<Card>();

  constructor(private modalService: NgbModal) {}

  openModalCard() {
    console.log('teste', this.card.classOfService?.serviceClass)
    const modalRef = this.modalService.open(CardInfoComponent);
    modalRef.componentInstance.card$ = this.card;
    modalRef.componentInstance.onUpdate = (updatedCard: Card) => {
      Object.assign(this.card, updatedCard);
    }
  }

}
