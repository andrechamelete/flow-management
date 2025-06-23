import { Component } from '@angular/core';

@Component({
  selector: 'app-queues',
  imports: [],
  templateUrl: './queues.component.html',
  styleUrl: './queues.component.scss'
})
export class QueuesComponent {
  //cards sem finishedAt
  //separar em cards que estao em stage de fila e cards que estao em stage de processo
  //somar a quantidade de card em fila e em processo
  //expor (qtdde em fila / (qtdde em fila + qtdde em processo)) e (qtde em processo / (qtdde em fila + qtdde em processo))
}
