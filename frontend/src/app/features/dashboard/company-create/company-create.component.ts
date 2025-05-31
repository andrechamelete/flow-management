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
        company: ['', Validators.required]
      });
    }

  createCompany() {
    const newCompany: Company = {
      id: 0,
      name: this.companyName
    };
    
    this.companyService.createCompany(newCompany).subscribe({
      next: (createdCompany) => {
        this.activeModal.close(createdCompany);
      },
      error: (error) => {
        console.error('Error creating company:', error);
      }
    });
  }
}
