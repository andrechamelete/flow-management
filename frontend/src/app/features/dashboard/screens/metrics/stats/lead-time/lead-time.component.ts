import { Component } from '@angular/core';

@Component({
  selector: 'app-lead-time',
  imports: [],
  templateUrl: './lead-time.component.html',
  styleUrl: './lead-time.component.scss'
})
export class LeadTimeComponent {
  //Card com finishedAt != null e com finishedAt dentro do range de data selecionado
  //para cada card, finishedAt - createdAt;
  //somar o lead time de todos os cards e dividir pela quantidade desses cards.
}
