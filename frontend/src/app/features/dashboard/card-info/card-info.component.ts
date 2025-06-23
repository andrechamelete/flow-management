import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Card } from '../../../models/card';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { SessionService } from '../../../service/session.service';
import { HttpClient } from '@angular/common/http';
import { ClassOfService } from '../../../models/ClassOfService';
import { BoardService } from '../../../service/board.service';
import { CardType } from '../../../models/CardType';

@Component({
  selector: 'app-card-info',
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './card-info.component.html',
  styleUrl: './card-info.component.scss'
})
export class CardInfoComponent implements OnInit {

  editCardForm: FormGroup;
  @Input() card$!: Card;
  @Input() onUpdate!: (updatedCard: Card) => void;
  classesOfService: ClassOfService[] = [];
  cardTypes: CardType[] = [];

  isEditing: boolean = false;
  assignedToEmail: string | undefined;
  description: string | undefined;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private http: HttpClient,
    private boardService: BoardService) {
      this.editCardForm = this.fb.group({
        name: ['', Validators.required],
        description: [''],
        assigneeEmail: [''],
        dueDate: [''],
        blocked: [''],
        classOfService: [''],
        type: ['']
      })
    }

  ngOnInit() {
    this.editCardForm.patchValue({
      name: this.card$.name,
      description: this.card$.description,
      assigneeEmail: this.card$.assignedTo?.email,
      dueDate: this.card$.dueDate,
      blocked: this.card$.blocked,
      classOfService: this.card$.classOfService?.id,
      type: this.card$.type
    });

    this.boardService.classOfService$.subscribe(data => {
      this.classesOfService = data;
    })

    this.boardService.cardType$.subscribe(data => {
      this.cardTypes = data;
    })
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  editCard() {
    if (this.editCardForm.valid) {
      const data = {
        name: this.editCardForm.value.name,
        description: this.editCardForm.value.description,
        assignedTo: this.editCardForm.value.assigneeEmail,
        dueDate: this.editCardForm.value.dueDate,
        blocked: this.editCardForm.value.blocked,
        classOfService: Number(this.editCardForm.value.classOfService),
        type: Number(this.editCardForm.value.type)
      }
      console.log("Card alterado: ", data);
      this.http.patch<Card>(`http://localhost:8080/board/card/${this.card$.id}`, data).subscribe({
        next: (response) => {
          console.log('Card updated successfully', response);
          this.card$ = { ...this.card$, ...response};
          this.boardService.updateCardLocal(this.card$);

        },
        error: (error) => {
          console.log('Error updating card', error);
        }
      });
      this.isEditing = !this.isEditing;
    }
  }

  getClassOfServiceNameById(id: number | undefined): string {
    if (!id) {
      return '';
    }

    const cos = this.classesOfService.find(c => c.id === id);
    return cos?.serviceClass || '';
  }

}
