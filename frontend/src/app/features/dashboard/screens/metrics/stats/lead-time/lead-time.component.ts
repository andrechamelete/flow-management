import { Component, OnInit, OnDestroy } from '@angular/core';
import { MetricsService } from '../../../../../../service/metrics.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lead-time',
  imports: [CommonModule],
  templateUrl: './lead-time.component.html',
  styleUrl: './lead-time.component.scss'
})
export class LeadTimeComponent implements OnInit, OnDestroy {

  leadTime: number | null = null;
  private subscription = new Subscription();

  constructor(private metricsService: MetricsService) {}

  ngOnInit(): void {
    this.subscription.add(
      this.metricsService.leadTime$.subscribe(lT => {
        console.log('lead time received: ', lT);
        this.leadTime = lT;
      })
    );      
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  //Card com finishedAt != null e com finishedAt dentro do range de data selecionado
  //para cada card, finishedAt - createdAt;
  //somar o lead time de todos os cards e dividir pela quantidade desses cards.
}
