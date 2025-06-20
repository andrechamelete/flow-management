import { Component, OnInit } from '@angular/core';
import { Flow } from '../../../models/flow';
import { FlowService } from '../../../service/flow.service';
import { SessionService } from '../../../service/session.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgbTypeaheadModule, NgbTypeaheadSelectItemEvent, NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, merge, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { FlowCreateComponent } from '../flows/flow-create/flow-create.component';
import { Company } from '../../../models/company';

@Component({
  selector: 'app-flows',
  imports: [CommonModule, NgbModule, FormsModule, NgbTypeaheadModule],
  templateUrl: './flows.component.html',
  styleUrl: './flows.component.scss'
})
export class FlowsComponent implements OnInit {

  flows: Flow[] = [];
  selectedFlow: Flow | null = null;
  errorMessage: string | null = null;
  selectedCompany: String | null = null;

  constructor(private sessionService: SessionService, private modalService: NgbModal,  private flowService: FlowService) { }

  get hasSelectedCompany(): boolean {
    return !!this.sessionService.getCompany();
  }

  ngOnInit(): void {
    this.sessionService.companyChanges$.subscribe(companyId => {
      this.selectedCompany = companyId;
      this.flowService.getFlows().subscribe({
        next: (data: Flow[]) => {
          this.flows = data.sort((a, b) => a.name.localeCompare(b.name));
          const savedFlowId = this.sessionService.getFlow();
          if(savedFlowId) {
            this.selectedFlow = this.flows.find(f => f.id.toString() === savedFlowId) || null;
          }
        },
        error: (err: any) => {
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
      })  
    });
  }

  private manualTrigger$ = new Subject<string>();

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const inputFocus$ = this.manualTrigger$;

    return merge(debouncedText$, inputFocus$).pipe(
      map(term =>
        term === ''
          ? this.flows
          : this.flows.filter(f => f.name.toLowerCase().indexOf(term.toLowerCase()) > -1)
      )
    );
  };

  formatter = (flow: Flow) => flow.name;

  onSelectItem(event: NgbTypeaheadSelectItemEvent<Flow>) {
    this.selectedFlow = event.item;
    this.sessionService.setFlow(event.item.id.toString());
  }

  toggleDropdown() {
    this.manualTrigger$.next('');
  }

  launchCreateFlow() {
    console.log("launchCreateFlow");
    const modalRef = this.modalService.open(FlowCreateComponent);

    modalRef.result.then((newFlow: Flow) => {
      this.flows.push(newFlow);
      this.flows = this.flows.sort((a, b) => a.name.localeCompare(b.name));
      this.selectedFlow = newFlow;
      this.sessionService.setFlow(newFlow.id.toString());
    }).catch((error) => {
      console.log('Modal dismissed', error);
    })
  }
}
