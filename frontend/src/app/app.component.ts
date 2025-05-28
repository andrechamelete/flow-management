import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
//import { HomeComponent } from './features/home/home.component';
import { RouterModule } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule, RouterModule],
  template: `
  <router-outlet></router-outlet>
  `,
  styleUrl: './app.component.scss'
})

export class AppComponent {

}
