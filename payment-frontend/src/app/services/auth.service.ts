import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth.model';

interface JwtPayload {
  sub: string;
  exp: number;
  roles: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'accessToken';
  private readonly usernameKey = 'username';

  // Transition mode: true = users stored statically in frontend. Final mode: false = JWT from Spring Boot.
  private readonly useStaticAuth = false;

  private readonly staticUsers = [
    { username: 'admin', password: '1234', roles: ['ADMIN', 'USER'] },
    { username: 'user1', password: '1234', roles: ['USER'] }
  ];

  constructor(private http: HttpClient, private router: Router) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    if (this.useStaticAuth) {
      const user = this.staticUsers.find(u => u.username === request.username && u.password === request.password);
      if (!user) throw new Error('Invalid credentials');
      const response = this.createStaticToken(user.username, user.roles);
      return of(response).pipe(tap(res => this.saveSession(res)));
    }

    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, request)
      .pipe(tap(response => this.saveSession(response)));
  }

  saveSession(response: LoginResponse): void {
    localStorage.setItem(this.tokenKey, response.accessToken);
    localStorage.setItem(this.usernameKey, response.username);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.usernameKey);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = jwtDecode<JwtPayload>(token);
      return !!payload.exp && payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  getRoles(): string[] {
    const token = this.getToken();
    if (!token) return [];
    try {
      const payload = jwtDecode<JwtPayload>(token);
      return payload.roles || [];
    } catch {
      return [];
    }
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.usernameKey);
    this.router.navigateByUrl('/login');
  }

  private createStaticToken(username: string, roles: string[]): LoginResponse {
    const header = { alg: 'none', typ: 'JWT' };
    const exp = Math.floor(Date.now() / 1000) + 24 * 60 * 60;
    const payload = { sub: username, roles, exp };
    const token = `${this.base64Url(header)}.${this.base64Url(payload)}.`;
    return {
      accessToken: token,
      tokenType: 'Bearer',
      expiresAt: new Date(exp * 1000).toISOString(),
      username,
      roles
    };
  }

  private base64Url(value: object): string {
    return btoa(JSON.stringify(value)).replace(/=/g, '').replace(/\+/g, '-').replace(/\//g, '_');
  }
}
