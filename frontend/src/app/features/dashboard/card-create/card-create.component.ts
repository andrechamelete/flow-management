import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../../../service/session.service';
import { Form, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Stage } from '../../../models/stage';

@Component({
  selector: 'app-card-create',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './card-create.component.html',
  styleUrl: './card-create.component.scss'
})
export class CardCreateComponent {
  
  @Input() stageId!: number;
  cardForm: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    private http: HttpClient,
    private sessionService: SessionService,
    private fb: FormBuilder) {
      this.cardForm = this.fb.group({
        name: ['', Validators.required],
        description: [''],
        dueDate: [''],
        assigneeEmail: [''],
        class_service: [''],
        type: ['']
    });
  }

  createCard() {
    console.log('form valid: ', this.cardForm.valid);
    if (this.cardForm.valid) {
      const data = {
        companyId: Number(this.sessionService.getCompany()),
        flowId: Number(this.sessionService.getFlow()),
        stageId: this.stageId,
        name: this.cardForm.value.name,
        description: this.cardForm.value.description,        
        dueDate: this.cardForm.value.dueDate,
        assigneeEmail: this.cardForm.value.assigneeEmail
        //class_service: this.cardForm.value.class_service,
        //type: this.cardForm.value.type,
        
      }
      console.log(data);
      this.http.post<Form>('http://localhost:8080/board/card', data).subscribe({
        next: (response) => {
          console.log(response);
          this.activeModal.close();
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

}
