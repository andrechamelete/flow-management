import { Component } from '@angular/core';
import { CfdComponent } from "./cfd/cfd.component";
import { AgingComponent } from "./aging/aging.component";
import { HistogramComponent } from "./histogram/histogram.component";
import { ScatterplotComponent } from "./scatterplot/scatterplot.component";

@Component({
  selector: 'app-charts',
  imports: [CfdComponent, AgingComponent, HistogramComponent, ScatterplotComponent],
  templateUrl: './charts.component.html',
  styleUrl: './charts.component.scss'
})
export class ChartsComponent {

}
