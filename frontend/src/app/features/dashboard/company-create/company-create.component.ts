import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CompanyService } from '../../../service/company.service';
import { Company } from '../../../models/company';
import { FormsModule } from '@angular/forms';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../../../service/session.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-company-create',
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './company-create.component.html',
  styleUrl: './company-create.component.scss'
})
export class CompanyCreateComponent {

  companyName = '';
  companyForm: FormGroup;

  constructor(
    public activeModal: NgbActiveModal, 
    private companyService: CompanyService,
    private sessionService: SessionService,
    private http: HttpClient,
    private fb: FormBuilder) {
      this.companyForm = this.fb.group({
        name: ['', Validators.required]
      });
    }

  createCompany() {
    if (this.companyForm.valid) {
      this.http.post<Company>('http://localhost:8080/companies', this.companyForm.value, {
        headers: {
          Authorization: 'Bearer' + this.sessionService.getToken()
        }
      }).subscribe({
          next: (response) => {
            console.log('Company created succestully', response);
            this.sessionService.setCompany(response.id.toString());
            this.activeModal.close(response);
          },
          error: (err) => {
            console.log('Failed to create company', err);
          }
        });
    }
  }
}
