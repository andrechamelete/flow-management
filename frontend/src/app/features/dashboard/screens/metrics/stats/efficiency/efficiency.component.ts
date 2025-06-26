import { Component, OnInit, OnDestroy } from '@angular/core';
import { MetricsService } from '../../../../../../service/metrics.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-efficiency',
  imports: [CommonModule],
  templateUrl: './efficiency.component.html',
  styleUrl: './efficiency.component.scss'
})
export class EfficiencyComponent implements OnInit, OnDestroy {

  inProcess: number | null = null;
  inQueue: number | null = null;
  widthProcesss: number | null = null;
  widthQueue: number | null = null;
  private subscription = new Subscription();

  constructor(private metricsService: MetricsService) {}

  ngOnInit(): void {
    this.subscription.add(
      this.metricsService.inProgress$.subscribe(iP => {
        console.log('in progress received: ', iP);
        this.inProcess = iP * 100;
        this.widthProcesss = this.inProcess * 2;
      })      
    );
    this.subscription.add(
      this.metricsService.inQueue$.subscribe(iQ => {
        console.log('in queue received: ', iQ);
        this.inQueue = iQ * 100;
        this.widthQueue = this.inQueue * 2;
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  //cycleTimes dentro do range selecionado
  // dividir entre stages de fila e stages de processo
  //somar os tempos de fila e de processo
  //expor (fila / (fila+processo)) e (processo / (fila+processo))
}
