import { Component, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NgClass } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ProjectBid } from '../../models/project-bid.model';
import { ProjectBidService } from '../../services/project-bid.service';

type BidListState = { bids: ProjectBid[]; loading: boolean; error: string };

@Component({
  selector: 'app-project-bid-list',
  templateUrl: './project-bid-list.component.html',
  styleUrls: ['./project-bid-list.component.css'],
  standalone: true,
  imports: [NgClass],
})
export class ProjectBidListComponent {
  private service = inject(ProjectBidService);
  private router = inject(Router);

  private state = toSignal(
    this.service.getAll().pipe(
      map((bids): BidListState => ({ bids, loading: false, error: '' })),
      catchError(() => of<BidListState>({ bids: [], loading: false, error: 'Failed to load project bids.' })),
    ),
    { initialValue: { bids: [], loading: true, error: '' } as BidListState },
  );

  bids = computed(() => this.state().bids);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  goTo(id: number): void {
    this.router.navigate(['/bids', id]);
  }

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }
}
