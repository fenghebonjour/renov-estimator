import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgClass, DecimalPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ProjectBid } from '../../models/project-bid.model';
import { ServiceOffer } from '../../models/service-offer.model';
import { ProjectBidService } from '../../services/project-bid.service';

type BidDetailState = { bid: ProjectBid | null; loading: boolean; error: string };

@Component({
  selector: 'app-project-bid-detail',
  templateUrl: './project-bid-detail.component.html',
  styleUrls: ['./project-bid-detail.component.css'],
  standalone: true,
  imports: [RouterLink, NgClass, DecimalPipe],
})
export class ProjectBidDetailComponent {
  private service = inject(ProjectBidService);
  private route = inject(ActivatedRoute);
  private id = Number(this.route.snapshot.paramMap.get('id'));

  private state = toSignal(
    this.service.getById(this.id).pipe(
      map((bid): BidDetailState => ({ bid, loading: false, error: '' })),
      catchError(() => of<BidDetailState>({ bid: null, loading: false, error: 'Project bid not found.' })),
    ),
    { initialValue: { bid: null, loading: true, error: '' } as BidDetailState },
  );

  bid = computed(() => this.state().bid);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }

  totalMaterials(offer: ServiceOffer): number {
    return (offer.materials || []).reduce((sum, m) => sum + m.quantity * m.unitPrice, 0);
  }

  totalLabor(offer: ServiceOffer): number {
    return (offer.laborItems || []).reduce((sum, m) => sum + m.quantity * m.unitPrice, 0);
  }
}
