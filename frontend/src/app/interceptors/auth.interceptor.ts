import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.authService.getToken();
    if (token) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        // A rejected token (expired/invalid) → clear it and bounce to login, so a
        // stale token can't strand you on a page whose data calls all 403.
        if ((err.status === 401 || err.status === 403) && !req.url.includes('/auth/')) {
          this.authService.logout();
        }
        return throwError(() => err);
      }),
    );
  }
}
