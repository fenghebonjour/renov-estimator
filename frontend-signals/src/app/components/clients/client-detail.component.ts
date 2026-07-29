import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgClass } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';

type ClientDetailState = { client: Client | null; loading: boolean; error: string };

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],
  standalone: true,
  imports: [RouterLink, NgClass],
})
export class ClientDetailComponent {
  private service = inject(ClientService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private id = Number(this.route.snapshot.paramMap.get('id'));

  private state = toSignal(
    this.service.getById(this.id).pipe(
      map((client): ClientDetailState => ({ client, loading: false, error: '' })),
      catchError(() => of<ClientDetailState>({ client: null, loading: false, error: 'Client not found.' })),
    ),
    { initialValue: { client: null, loading: true, error: '' } as ClientDetailState },
  );

  client = computed(() => this.state().client);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  goToBid(id: number): void {
    this.router.navigate(['/bids', id]);
  }

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }
}
