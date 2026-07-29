import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

export interface AuthResponse {
  token: string;
  userId: number;
  userType: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private url = `${environment.apiUrl}/auth`;

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.url}/login`, { username, password }).pipe(
      tap((res: AuthResponse) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('userId', String(res.userId));
        localStorage.setItem('userType', res.userType);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('userType');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const exp = JSON.parse(atob(token.split('.')[1])).exp;   // JWT expiry, in seconds
      return typeof exp === 'number' && exp * 1000 > Date.now();
    } catch {
      return false;   // malformed token → treat as logged out
    }
  }

  getCurrentUsername(): string | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      return JSON.parse(atob(token.split('.')[1])).sub;
    } catch {
      return null;
    }
  }
}
