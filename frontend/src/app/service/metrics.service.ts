import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { BehaviorSubject, tap, combineLatest, startWith } from 'rxjs';
import { MetricsResponse } from '../models/DTO/MetricsResponse';

@Injectable({
  providedIn: 'root'
})
export class MetricsService {

  private startDateSubject =  new BehaviorSubject<Date | null>(null);
  private endDateSubject =  new BehaviorSubject<Date | null>(null);
  startDate$ = this.startDateSubject.asObservable();
  endDate$ = this.endDateSubject.asObservable();

  updateDateRange(start: Date, end: Date) {
    this.startDateSubject.next(start);
    this.endDateSubject.next(end);
  }

  private leadTimeSubject = new BehaviorSubject<number>(0);
  private throughputSubject = new BehaviorSubject<number>(0);
  private inProgressSubject = new BehaviorSubject<number>(0);
  private inQueueSubject = new BehaviorSubject<number>(0);
  private queueSubject = new BehaviorSubject<number>(0);
  private wipSubject = new BehaviorSubject<number>(0);
  leadTime$ = this.leadTimeSubject.asObservable();
  throughput$ = this.throughputSubject.asObservable();
  inProgress$ = this.inProgressSubject.asObservable();
  inQueue$ = this.inQueueSubject.asObservable();
  wip$ = this.wipSubject.asObservable();
  queue$ = this.queueSubject.asObservable();

  leadTime: number = 0;
  throughput: number = 0;
  inProgress: number = 0;
  inQueue: number = 0;
  wip: number = 0;
  queue: number = 0;

  constructor(private http: HttpClient, private sessionService: SessionService) {
    this.sessionService.companyChanges$.subscribe(company => this.tryLoadMetrics());
    this.sessionService.flowChanges$.subscribe(flow => this.tryLoadMetrics());
    this.tryLoadMetrics();
    combineLatest([
  this.startDate$,
  this.endDate$,
  this.sessionService.companyChanges$.pipe(startWith(this.sessionService.getCompany())),
  this.sessionService.flowChanges$.pipe(startWith(this.sessionService.getFlow()))
]).subscribe(([start, end, companyId, flowId]) => {
  if (start && end && companyId && flowId) {
    this.loadMetrics(Number(companyId), Number(flowId), start, end).subscribe();
    console.log('parametros enviados:', companyId, flowId, start, end);
  }
});
  }

  tryLoadMetrics() {
    const companyId = Number(this.sessionService.getCompany());
    const flowId = Number(this.sessionService.getFlow());
    const startDate = this.startDateSubject.value;
    const endDate = this.endDateSubject.value;
    if (companyId && flowId && startDate && endDate) {
      this.loadMetrics(companyId, flowId, this.startDateSubject.value, this.endDateSubject.value).subscribe();
      console.log('negocios enviados:', companyId, flowId, startDate, endDate);

    }
  }

  loadMetrics(companyId: number, flowId: number, start: Date | null, end: Date | null) {
    return this.http.get<MetricsResponse>(`http://localhost:8080/metrics?companyId=${companyId}&flowId=${flowId}&startDate=${start?.toISOString()}&endDate=${end?.toISOString()}`)
    .pipe(tap(response => {
      this.leadTimeSubject.next(response.leadTime.leadTime);
      this.leadTime = response.leadTime.leadTime;
      this.throughputSubject.next(response.throughput.throughput);
      this.throughput = response.throughput.throughput;
      this.inProgressSubject.next(response.efficiency.inProcess);
      this.inProgress = response.efficiency.inProcess;
      this.inQueueSubject.next(response.efficiency.inQueue);
      this.inQueue = response.efficiency.inQueue;
      this.wipSubject.next(response.queue.wip);
      this.wip = response.queue.wip;
      this.queueSubject.next(response.queue.queue);
      console.log("parametros passados:", companyId, flowId, start, end);
    }));
  }   
}
