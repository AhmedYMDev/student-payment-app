import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error = '';
  form = this.fb.group({
    username: ['admin', Validators.required],
    password: ['1234', Validators.required]
  });

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {}

  login(): void {
    if (this.form.invalid) return;
    const request = {
      username: this.form.value.username || '',
      password: this.form.value.password || ''
    };
    this.authService.login(request).subscribe({
      next: () => this.router.navigateByUrl('/dashboard'),
      error: () => this.error = 'Login ou mot de passe incorrect'
    });
  }
}
