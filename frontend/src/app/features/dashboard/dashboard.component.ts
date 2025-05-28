import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Company } from '../../models/company';
import { CompanyService } from '../../service/company.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, of, merge, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { NgbTypeaheadSelectItemEvent, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: true, // talvez precise tirar
  imports: [CommonModule, NgbTypeaheadModule, FormsModule]
})

export class DashboardComponent implements OnInit {

  companies: Company[] = [];
  loading: boolean = true;
  errorMessage: string | null = null;

  constructor(private companyService: CompanyService) { }

  ngOnInit(): void {
    this.loading = true;
    this.companyService.getMyCompanies().subscribe({
        next: (data: Company[]) => {
            this.companies = data;
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

selectedCompany: Company | null = null;
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
    this.selectedCompany = event.item; // Set selected company
    // Do something with the selected company, e.g., update a form
  }

  toggleDropdown() {
    this.manualTrigger$.next('');
  }

  lauchCreateCompany(): void {
    alert('Create Company');
  }
}
