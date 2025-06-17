import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Card } from '../../../models/card';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-card-info',
  imports: [FormsModule, CommonModule],
  templateUrl: './card-info.component.html',
  styleUrl: './card-info.component.scss'
})
export class CardInfoComponent implements OnInit {

  @Input() card$!: Card;

  isEditing: boolean = false;
  assignedToEmail: string | undefined;
  description: string | undefined;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.assignedToEmail = this.card$.assignedTo?.email;
    this.description = this.card$.description;
  }

  save() {
    this.isEditing = !this.isEditing;

  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  } 

}
