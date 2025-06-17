import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FlowService } from '../../../../service/flow.service';
import { FormsModule } from '@angular/forms';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../../../../service/session.service';
import { CommonModule } from '@angular/common';
import { Flow } from '../../../../models/flow';

@Component({
  selector: 'app-flow-create',
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './flow-create.component.html',
  styleUrl: './flow-create.component.scss'
})
export class FlowCreateComponent {

  flowForm: FormGroup;
  flowName = '';
  flowDescription = '';
  flowDepartment = '';

  constructor(
    public activeModal: NgbActiveModal,
    private flowService: FlowService,
    private sessionService: SessionService,
    private http: HttpClient,
    private fb: FormBuilder) {
      this.flowForm = this.fb.group({
        name: ['', Validators.required],
        description: [''],
        department: ['']
    });
  }

  createFlow() {
    if (this.flowForm.valid) {
      const data = {
        name: this.flowForm.value.name,
        description: this.flowForm.value.description,
        department: this.flowForm.value.department,
        companyId: Number(this.sessionService.getCompany())
      }
      this.http.post<Flow>('http://localhost:8080/flows', data, {
        headers: {
          Authorization: 'Bearer' + this.sessionService.getToken()
        }
      }).subscribe({
        next: (response) => {
          console.log('Flow created succestully', response);
          this.sessionService.setFlow(response.id.toString());
          this.activeModal.close(response);
        },
        error: (err) => {
          console.log('Failed to create flow', err);
        }
      });
    }
  }
}
