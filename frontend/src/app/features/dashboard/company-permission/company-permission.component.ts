import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { SessionService } from '../../../service/session.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-company-permission',
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './company-permission.component.html',
  styleUrl: './company-permission.component.scss'
})
export class CompanyPermissionComponent {

  permissionForm: FormGroup;

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private sessionService: SessionService,
    public activeModal: NgbActiveModal) {
      this.permissionForm = this.fb.group({
        email: ['', Validators.required]
      });
    }

  givePermission() {
    if (this.permissionForm.valid) {
      const data = {
        email: this.permissionForm.value.email,
        companyId: Number(this.sessionService.getCompany())
    };
      this.http.post('http://localhost:8080/companies/permission', data, {
        headers: {
          Authorization: 'Bearer' + this.sessionService.getToken()
        }
      }).subscribe({
        next: (response) => {
          console.log("permission granted", response);
          this.activeModal.close(response);
        },
        error: (err) => {
          console.log("failed to grant permission", err);
        }
      })
    }
  }    
}
