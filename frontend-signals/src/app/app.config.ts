import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth.interceptor';

// The standalone equivalent of AppModule's @NgModule({ imports, providers }).
// Every app-wide provider lives here; bootstrapApplication() consumes it.
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    // Functional interceptor registered the modern way — no HTTP_INTERCEPTORS token.
    provideHttpClient(withInterceptors([authInterceptor])),
  ],
};
