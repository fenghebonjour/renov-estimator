import { inject } from '@angular/core';
import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

// Functional interceptor: a function typed as HttpInterceptorFn, registered via
// withInterceptors([authInterceptor]) in app.config.ts. `next` is called directly
// (next(req)) rather than next.handle(req) as in the old class-based interceptor.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const token = auth.getToken();
  if (token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }
  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      // A rejected token (expired/invalid) → clear it and bounce to login, so a
      // stale token can't strand you on a page whose data calls all 403.
      if ((err.status === 401 || err.status === 403) && !req.url.includes('/auth/')) {
        auth.logout();
      }
      return throwError(() => err);
    }),
  );
};
