import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Company } from '../../models/company';
import { CompanyService } from '../../service/company.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, merge, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { NgbModal, NgbTypeaheadSelectItemEvent, NgbTypeaheadModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CompanyCreateComponent } from './company-create/company-create.component';
import { SessionService } from '../../service/session.service';
import { Router } from '@angular/router';
import { CompanyPermissionComponent } from './company-permission/company-permission.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: true,
  imports: [CommonModule, NgbTypeaheadModule, FormsModule, NgbModule]
})

export class DashboardComponent implements OnInit {

  companies: Company[] = [];
  loading: boolean = true;
  errorMessage: string | null = null;
  selectedCompany: Company | null = null;

  constructor(
    private companyService: CompanyService, 
    private modalService: NgbModal, 
    private sessionService: SessionService,
    private router: Router) { }

  ngOnInit(): void {
    this.loading = true;
    this.companyService.getMyCompanies().subscribe({
        next: (data: Company[]) => {
            this.companies = data.sort((a, b) => a.name.localeCompare(b.name));
            const savedCompanyId = this.sessionService.getCompany();
            if (savedCompanyId) {
              this.selectedCompany = this.companies.find(c => c.id.toString() === savedCompanyId) || null;
            }
            this.loading = false;

        },
        error: (err: any) => {
            this.loading = false;
            if (err instanceof HttpErrorResponse) {
                if (err.status === 0) {
                    this.errorMessage = 'Could not connect to the server. Please check your network connection.';
                } else if (err.status === 401 || err.status === 403) {
                    this.errorMessage = 'Unauthorized. Please log in.';
                } else {
                    this.errorMessage = `Server error: ${err.status} - ${err.statusText}. Please try again later.`;
                    console.error('Error loading companies:', err); 
                }
            } else {
                this.errorMessage = 'An unknown error occurred. Please try again later.';
                console.error('Error loading companies:', err);
            }
        }
    });
  }

  
  private manualTrigger$ = new Subject<string>();


  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const inputFocus$ = this.manualTrigger$;

    return merge(debouncedText$, inputFocus$).pipe(
      map(term =>
        term === ''
          ? this.companies
          : this.companies.filter(c => c.name.toLowerCase().indexOf(term.toLowerCase()) > -1)
      )
    );
  };

  formatter = (company: Company) => company.name;

  onSelectItem(event: NgbTypeaheadSelectItemEvent<Company>) {
    this.selectedCompany = event.item;
    this.sessionService.setCompany(event.item.id.toString());
  }

  toggleDropdown() {
    this.manualTrigger$.next('');
  }

  lauchCreateCompany() {
    console.log("lauchCreateCompany");
    const modalRef = this.modalService.open(CompanyCreateComponent);
    modalRef.result.then((result) => {
      if(result) {
        this.companies.push(result);
        this.selectedCompany = result;
      }
    }).catch((reason) => {});
  }

  launchGivePermission() {
    console.log("launch give permission");
    const modalRef = this.modalService.open(CompanyPermissionComponent);
  }

  unlogging(): void {
    this.sessionService.clearSession();
    this.router.navigate(['/home'])
  }
}
