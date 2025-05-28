import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CompanyService } from '../../../service/company.service';
import { Company } from '../../../models/company';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-company-create',
  imports: [FormsModule],
  templateUrl: './company-create.component.html',
  styleUrl: './company-create.component.scss'
})
export class CompanyCreateComponent {

  companyName = '';

  constructor(public activeModal: NgbActiveModal, private companyService: CompanyService) {}

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
