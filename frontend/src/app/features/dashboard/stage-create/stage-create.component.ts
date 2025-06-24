import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { SessionService } from '../../../service/session.service';
import { HttpClient } from '@angular/common/http';
import { Stage } from '../../../models/stage';
import { BoardComponent } from '../screens/board/board.component';

@Component({
  selector: 'app-stage-create',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './stage-create.component.html',
  styleUrl: './stage-create.component.scss'
})
export class StageCreateComponent {

  stageForm: FormGroup;

  constructor(public activeModal: NgbActiveModal, 
              private fb: FormBuilder, 
              private sessionService: SessionService, 
              private http: HttpClient) {
    this.stageForm = this.fb.group({
      name: ['', Validators.required],
      wipLimit: [''],
      type: ['', Validators.required]
    })
  }

  createStage() {
    if (this.stageForm.valid) {
      const data = {
        name: this.stageForm.value.name,
        companyId: Number(this.sessionService.getCompany()),
        flowId: Number(this.sessionService.getFlow()),
        wipLimit: this.stageForm.value.wipLimit,
        type: this.stageForm.value.type

      }
      console.log(data)
      this.http.post<Stage>('http://localhost:8080/board/stage', data).subscribe({
        next: (response) => {
          console.log('Stage created successfully', response);
          this.activeModal.close(response);
        },
        error: (error) => {
          console.log('Error creating stage', error);
        }
      })
    }
  }
}
