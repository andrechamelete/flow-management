import { Component, OnInit, OnDestroy } from '@angular/core';
import { MetricsService } from '../../../../../../service/metrics.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-queues',
  imports: [CommonModule],
  templateUrl: './queues.component.html',
  styleUrl: './queues.component.scss'
})
export class QueuesComponent implements OnInit, OnDestroy {

  wip: number = 0;
  queue: number = 0;
  widthWip: number = 0;
  widthQueue: number = 0;
  private subscription = new Subscription();

  constructor(private metricsService: MetricsService) {}

  ngOnInit(): void {
    this.subscription.add(
      this.metricsService.wip$.subscribe(wip => {
        console.log('wip received: ', wip);
        this.wip = wip;
        this.updateWidths();
        
      })
    );
    this.subscription.add(
      this.metricsService.queue$.subscribe(queue => {
        console.log('queue received: ', queue);
        this.queue = queue;
        this.updateWidths();
      })
    );     
  }

  updateWidths(): void {
    const total = this.wip + this.queue;
    if (total > 0) {
      this.widthWip = (this.wip/total) * 100;
      this.widthQueue = (this.queue/total) * 100;
    } else {
      this.widthWip = 0;
      this.widthQueue = 0;
    }
  }

  ngOnDestroy(): void {
      this.subscription.unsubscribe();
  }

  //cards sem finishedAt
  //separar em cards que estao em stage de fila e cards que estao em stage de processo
  //somar a quantidade de card em fila e em processo
  //expor (qtdde em fila / (qtdde em fila + qtdde em processo)) e (qtde em processo / (qtdde em fila + qtdde em processo))
}
