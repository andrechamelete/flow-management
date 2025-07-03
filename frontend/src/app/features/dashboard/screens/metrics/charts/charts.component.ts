import { Component, OnInit, OnDestroy } from '@angular/core';
import { CfdComponent } from "./cfd/cfd.component";
import { AgingComponent } from "./aging/aging.component";
import { HistogramComponent } from "./histogram/histogram.component";
import { ScatterplotComponent } from "./scatterplot/scatterplot.component";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-charts',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, CfdComponent, AgingComponent, HistogramComponent, ScatterplotComponent],
  templateUrl: './charts.component.html',
  styleUrl: './charts.component.scss'
})
export class ChartsComponent {

  chartForm: FormGroup;

  constructor(
    private fb: FormBuilder) {
      this.chartForm = this.fb.group({
        chart: ['cfd']
      })
  }
}
