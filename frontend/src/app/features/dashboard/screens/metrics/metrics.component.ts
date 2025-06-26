import { Component, OnDestroy } from '@angular/core';
import { LeadTimeComponent } from "./stats/lead-time/lead-time.component";
import { ThroughputComponent } from "./stats/throughput/throughput.component";
import { EfficiencyComponent } from "./stats/efficiency/efficiency.component";
import { QueuesComponent } from "./stats/queues/queues.component";
import { BehaviorSubject, Subscription } from 'rxjs';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule}  from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ChartsComponent } from "./charts/charts.component";
import { CommonModule } from '@angular/common';
import { MetricsService } from '../../../../service/metrics.service';

@Component({
  selector: 'app-metrics',
  imports: [LeadTimeComponent,
    ThroughputComponent,
    EfficiencyComponent,
    QueuesComponent,
    MatFormFieldModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule, ChartsComponent, CommonModule],
  providers: [provideNativeDateAdapter()],
  templateUrl: './metrics.component.html',
  styleUrl: './metrics.component.scss'
})
export class MetricsComponent implements OnDestroy {
  private subscription = new Subscription();

  constructor(private metricsService: MetricsService) {
    const today = new Date();
    const lastThirdyDays = new Date(today.getTime() - (30 *24 * 60 * 60 * 1000));
    this.range.patchValue({
      start: lastThirdyDays,
      end: today,
    });
    this.onDateChange();
  }

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  startDate: Date | null = null;
  endDate: Date | null = null;

  onDateChange(): void {
    const start = this.range.value.start;
    const end = this.range.value.end;
    if (start && end) {
      console.log('Selected date range:', start, end);
      this.metricsService.updateDateRange(start, end);
      this.startDate = start;
      this.endDate = end;
    }    
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
