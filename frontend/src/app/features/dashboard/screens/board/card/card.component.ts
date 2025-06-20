import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Card } from '../../../../../models/card';
import { CommonModule } from '@angular/common';
import { CardInfoComponent } from '../../../card-info/card-info.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClassOfService } from '../../../../../models/ClassOfService';
import { SessionService } from '../../../../../service/session.service';

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

  constructor(private modalService: NgbModal, private sessionService: SessionService) {}

  ngOnInit() {
    this.sessionService.classesOfServiceChanges$.subscribe(data => {
      this.classesOfService = data;
    })
  }

  openModalCard() {
    console.log('classe de serviço: ', this.card.classOfService?.id)
    const modalRef = this.modalService.open(CardInfoComponent);
    modalRef.componentInstance.card$ = this.card;
    modalRef.componentInstance.onUpdate = (updatedCard: Card) => {
      Object.assign(this.card, updatedCard);
    }
  }
/*
  getClassOfServiceNameById(id: number | undefined): string {
    if (!id) {
      return '';
    }
    const cos = this.classesOfService.find(c => c.id === id);
    return cos?.serviceClass || '';
  }
*/
}
