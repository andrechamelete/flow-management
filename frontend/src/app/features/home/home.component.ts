import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  activeTab: 'login' | 'register' = 'login';

  loginForm: FormGroup;
  registerForm: FormGroup;
  showPassword: boolean = false;
  loginErr: string = '';
  registerErr: string = '';

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router, private sessionService: SessionService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]

    });

    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  switchTab(tab: 'login' | 'register') {
    this.activeTab = tab;
  }

  onLogin() {
    if(this.loginForm.valid) {
      this.http.post<{token: string}>('http://localhost:8080/auth/login', this.loginForm.value)
        .subscribe({
          next: (response) => {
            console.log('Login success: ', response);
            this.sessionService.setToken(response.token);
            this.router.navigate(['/dashboard']);
          },
          error: (error) => {
            this.loginErr = error.error;
            console.log('Login failed: ', error)
          }
        })
    }
  }

  onRegister() {
    if(this.registerForm.valid) {
      this.http.post<{token: string}>('http://localhost:8080/auth/register', this.registerForm.value)
        .subscribe({
          next: (response) => {
            console.log('Register success: ', response);
            this.sessionService.setToken(response.token);
            this.router.navigate(['/dashboard']);
          },
          error: (error) => {
            this.registerErr = error.error;
            console.log('Register failed: ', error)
          }
        })
    }
  }
}
