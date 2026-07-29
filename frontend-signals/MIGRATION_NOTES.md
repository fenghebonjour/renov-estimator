# Modern Angular Migration — Cheat-Sheet

This folder is the **signals / standalone** twin of `../frontend`. Same app, same backend
(`http://localhost:8080`), same screens — only the Angular patterns changed. Diff any file
here against the same path under `../frontend` to see the *before → after* for one concept.

> Run classic and modern side by side:
> `cd ../frontend && ng serve` (→ :4200) and `cd frontend-signals && ng serve --port 4300` (→ :4300).

---

## The four changes, old → new

### 1. Bootstrap: NgModule → standalone

| Classic (`../frontend`) | Modern (here) |
|---|---|
| `app.module.ts` with `@NgModule({ declarations, imports, providers })` | **deleted** |
| `app-routing.module.ts` + `RouterModule.forRoot(routes)` | [`app.routes.ts`](src/app/app.routes.ts) — a bare `Routes` array |
| providers spread across the module | [`app.config.ts`](src/app/app.config.ts) — `ApplicationConfig` |
| `main.ts` → `platformBrowserDynamic().bootstrapModule(AppModule)` | [`main.ts`](src/main.ts) → `bootstrapApplication(AppComponent, appConfig)` |
| every component listed in `declarations` | each component sets `standalone: true` + its own `imports: [...]` |

**Consequence you feel:** a new component is *not* registered in a module. Instead it imports
exactly the directives/pipes its template uses (`RouterLink`, `ReactiveFormsModule`, `NgClass`,
`DecimalPipe`, …). Forget one and the compiler tells you.

### 2. Guard & interceptor: class → function

| Classic | Modern |
|---|---|
| `@Injectable class AuthGuard { canActivate() {...} }` | [`authGuard: CanActivateFn`](src/app/guards/auth.guard.ts) — a function |
| `@Injectable class AuthInterceptor implements HttpInterceptor` | [`authInterceptor: HttpInterceptorFn`](src/app/interceptors/auth.interceptor.ts) |
| deps via constructor | deps via `inject(AuthService)` |
| interceptor registered with the `HTTP_INTERCEPTORS` multi-provider | `provideHttpClient(withInterceptors([authInterceptor]))` |
| `next.handle(req)` | `next(req)` |

### 3. Dependency injection: constructor → `inject()`

```ts
// classic
constructor(private service: ClientService, private router: Router) {}

// modern
private service = inject(ClientService);
private router = inject(Router);
```

Applied to every component, service, guard and interceptor here.

### 4. State: fields + `subscribe` → **signals** (with `toSignal()` for HTTP)

Two patterns, chosen by the kind of screen:

**a) Read screens (lists + details) → `toSignal()`**
The HTTP Observable is turned straight into a signal. No `ngOnInit`, no manual `subscribe`,
no `unsubscribe`. RxJS still does the async work (`map`, `catchError`); the result lands in a signal.

```ts
// src/app/components/clients/client-list.component.ts
private state = toSignal(
  this.service.getAll().pipe(
    map((clients): State => ({ clients, loading: false, error: '' })),
    catchError(() => of<State>({ clients: [], loading: false, error: 'Failed to load clients.' })),
  ),
  { initialValue: { clients: [], loading: true, error: '' } as State },
);
clients = computed(() => this.state().clients);
loading = computed(() => this.state().loading);
error   = computed(() => this.state().error);
```

**b) Write screens (forms) → plain `signal()` updated with `.set()`**
The form and the submit call stay RxJS (a `subscribe` on the POST/PUT); only the *view state*
(`saving`, `error`, `loadingContractors`, …) becomes a signal.

```ts
saving = signal(false);
error  = signal('');
// ...
this.saving.set(true);
req$.subscribe({ error: () => { this.error.set('Failed to save.'); this.saving.set(false); } });
```

**c) Local UI state → `signal()` + `computed()`** (no HTTP at all)
See [`jvm-metrics.component.ts`](src/app/components/labo/jvm-metrics.component.ts): `activeId = signal(...)`,
`active = computed(...)`, toggled with `activeId.update(...)`. Proof that signals aren't just for async data.

**Templates:** a signal is read by *calling* it — `loading` → `loading()`, `clients` → `clients()`.
For the detail screens the value is aliased once with `@if (bid(); as bid) { … bid.type … }`
so the inner markup stays unchanged.

---

## The key insight (say this in the interview)

> **RxJS is not gone. Signals hold state; RxJS handles streams; `toSignal()` bridges them.**

- `HttpClient` still returns Observables here — every service is unchanged from the classic app.
- `toSignal()` / `.subscribe()` are the two ways that async result reaches a signal.
- Signals replaced *manual change-detection state* (component fields), not RxJS.

## What was deliberately **not** changed
- The services (`*.service.ts`) — still plain `HttpClient` returning `Observable`; only DI style (`inject()`) changed.
- Reactive forms — still `UntypedFormGroup` / `Validators`; forms weren't the migration target.
- Zone change detection is kept (`provideZoneChangeDetection`), not zoneless — a separate, bigger step.
- The backend, the API contract, the models, and all CSS are identical.

## File map (open these first)
1. [`main.ts`](src/main.ts) + [`app.config.ts`](src/app/app.config.ts) + [`app.routes.ts`](src/app/app.routes.ts) — the new bootstrap
2. [`auth.guard.ts`](src/app/guards/auth.guard.ts) + [`auth.interceptor.ts`](src/app/interceptors/auth.interceptor.ts) — functional versions
3. [`client-list.component.ts`](src/app/components/clients/client-list.component.ts) — `toSignal()` read screen
4. [`client-form.component.ts`](src/app/components/clients/client-form.component.ts) — `signal()` write screen
5. [`jvm-metrics.component.ts`](src/app/components/labo/jvm-metrics.component.ts) — `signal()` + `computed()` local state
