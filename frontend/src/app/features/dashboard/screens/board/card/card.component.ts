import { Component, Input } from '@angular/core';
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

  constructor(private modalService: NgbModal) {}

  openModalCard() {
    const modalRef = this.modalService.open(CardInfoComponent);
    modalRef.componentInstance.card$ = this.card;
    console.log('card info', this.card);
  }

}
