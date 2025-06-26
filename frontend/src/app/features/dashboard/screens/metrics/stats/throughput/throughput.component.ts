import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { MetricsService } from '../../../../../../service/metrics.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-throughput',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './throughput.component.html',
  styleUrl: './throughput.component.scss'
})
export class ThroughputComponent {

  throughputForm: FormGroup;
  throughput: number | null = null;
  private subscription = new Subscription();

  constructor(
    private metricsService: MetricsService,
    private fb: FormBuilder) {
      this.throughputForm = this.fb.group({
        time: ['week']
      })
  }

  get adjustedThroughput(): number {
    const base =  this.throughput ?? 0;
    const timeUnit = this.throughputForm.get('time')?.value;
    switch(timeUnit) {
      case 'week': return base * 7;
      case 'month': return base * 30;
      case 'day':
      default: return base;
    }
  }

  ngOnInit(): void {
    this.subscription.add(
      this.metricsService.throughput$.subscribe(tp => {
        console.log('throughput received: ', tp);
        this.throughput = tp;
      })
    )
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  //cards com finishedAt dentro do range selecionado
  //data fim selecionada - (dataFim - 1 dia|semana|mes)
  //somar cards em cada período (dia|semana|mes)
  //expor a quantidade de cada período
}
