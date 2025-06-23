import { Component } from '@angular/core';

@Component({
  selector: 'app-throughput',
  imports: [],
  templateUrl: './throughput.component.html',
  styleUrl: './throughput.component.scss'
})
export class ThroughputComponent {
  //cards com finishedAt dentro do range selecionado
  //data fim selecionada - (dataFim - 1 dia|semana|mes)
  //somar cards em cada período (dia|semana|mes)
  //expor a quantidade de cada período
}
