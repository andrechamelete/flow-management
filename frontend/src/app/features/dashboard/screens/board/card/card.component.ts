import { Component, Input } from '@angular/core';
//import { Stage } from '../../../../../models/stage';
import { Card } from '../../../../../models/card';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-card',
  imports: [CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  
  @Input() card!: Card;

}
