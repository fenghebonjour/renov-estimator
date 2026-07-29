import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Contractor } from '../../models/contractor.model';
import { ContractorService } from '../../services/contractor.service';

type ContractorListState = { contractors: Contractor[]; loading: boolean; error: string };

@Component({
  selector: 'app-contractor-list',
  templateUrl: './contractor-list.component.html',
  styleUrls: ['./contractor-list.component.css'],
  standalone: true,
  imports: [RouterLink],
})
export class ContractorListComponent {
  private service = inject(ContractorService);
  private router = inject(Router);

  private state = toSignal(
    this.service.getAll().pipe(
      map((contractors): ContractorListState => ({ contractors, loading: false, error: '' })),
      catchError(() => of<ContractorListState>({ contractors: [], loading: false, error: 'Failed to load contractors.' })),
    ),
    { initialValue: { contractors: [], loading: true, error: '' } as ContractorListState },
  );

  contractors = computed(() => this.state().contractors);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  goTo(id: number): void {
    this.router.navigate(['/contractors', id]);
  }

  displayName(c: Contractor): string {
    return c.firstName ? `${c.firstName} ${c.lastName}` : (c.name || c.username);
  }
}
