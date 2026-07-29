import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';

// One small object holds the whole view state so a single signal drives the template.
type ClientListState = { clients: Client[]; loading: boolean; error: string };

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css'],
  standalone: true,
  imports: [RouterLink],
})
export class ClientListComponent {
  private service = inject(ClientService);
  private router = inject(Router);

  // toSignal() subscribes to the HTTP stream and exposes its latest value as a signal.
  // RxJS still does the async work (map/catchError); the result lands in a signal.
  // No ngOnInit, no manual subscribe, no manual unsubscribe.
  private state = toSignal(
    this.service.getAll().pipe(
      map((clients): ClientListState => ({ clients, loading: false, error: '' })),
      catchError(() => of<ClientListState>({ clients: [], loading: false, error: 'Failed to load clients.' })),
    ),
    { initialValue: { clients: [], loading: true, error: '' } as ClientListState },
  );

  // Derived signals the template reads as clients(), loading(), error().
  clients = computed(() => this.state().clients);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  goTo(id: number): void {
    this.router.navigate(['/clients', id]);
  }
}
