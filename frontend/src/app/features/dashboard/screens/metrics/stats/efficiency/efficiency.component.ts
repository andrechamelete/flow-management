import { Component } from '@angular/core';

@Component({
  selector: 'app-efficiency',
  imports: [],
  templateUrl: './efficiency.component.html',
  styleUrl: './efficiency.component.scss'
})
export class EfficiencyComponent {
  //cycleTimes dentro do range selecionado
  // dividir entre stages de fila e stages de processo
  //somar os tempos de fila e de processo
  //expor (fila / (fila+processo)) e (processo / (fila+processo))
}
