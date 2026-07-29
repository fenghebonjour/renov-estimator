import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DecimalPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Contractor } from '../../models/contractor.model';
import { ContractorService } from '../../services/contractor.service';

type ContractorDetailState = { contractor: Contractor | null; loading: boolean; error: string };

@Component({
  selector: 'app-contractor-detail',
  templateUrl: './contractor-detail.component.html',
  styleUrls: ['./contractor-detail.component.css'],
  standalone: true,
  imports: [RouterLink, DecimalPipe],
})
export class ContractorDetailComponent {
  private service = inject(ContractorService);
  private route = inject(ActivatedRoute);
  private id = Number(this.route.snapshot.paramMap.get('id'));

  private state = toSignal(
    this.service.getById(this.id).pipe(
      map((contractor): ContractorDetailState => ({ contractor, loading: false, error: '' })),
      catchError(() => of<ContractorDetailState>({ contractor: null, loading: false, error: 'Contractor not found.' })),
    ),
    { initialValue: { contractor: null, loading: true, error: '' } as ContractorDetailState },
  );

  contractor = computed(() => this.state().contractor);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  displayName(c: Contractor): string {
    return c.firstName ? `${c.firstName} ${c.lastName}` : (c.name || c.username);
  }

  isIndividual(c: Contractor): boolean {
    return c.type?.toLowerCase() === 'individual';
  }
}
