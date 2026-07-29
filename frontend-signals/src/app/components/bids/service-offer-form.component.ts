import { Component, OnInit, inject, signal } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ContractorService } from '../../services/contractor.service';
import { Contractor } from '../../models/contractor.model';

@Component({
  selector: 'app-service-offer-form',
  templateUrl: './service-offer-form.component.html',
  styleUrls: ['./service-offer-form.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
})
export class ServiceOfferFormComponent implements OnInit {
  private fb = inject(UntypedFormBuilder);
  private contractorService = inject(ContractorService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  form!: UntypedFormGroup;
  bidId!: number;
  contractors = signal<Contractor[]>([]);
  loadingContractors = signal(true);
  saving = signal(false);
  error = signal('');

  ngOnInit(): void {
    this.bidId = Number(this.route.snapshot.paramMap.get('bidId'));
    this.form = this.fb.group({
      contractorId: ['', Validators.required],
      offerDate:    ['', Validators.required],
      validUntil:   ['', Validators.required],
      amount:       ['', [Validators.required, Validators.min(0)]],
      status:       ['created', Validators.required],
    });

    this.contractorService.getAll().subscribe({
      next: data => { this.contractors.set(data); this.loadingContractors.set(false); },
      error: () => { this.error.set('Failed to load contractors.'); this.loadingContractors.set(false); }
    });
  }

  displayName(c: Contractor): string {
    return c.firstName ? `${c.firstName} ${c.lastName}` : (c.name || c.username);
  }

  isInvalid(field: string): boolean {
    const ctrl = this.form.get(field);
    return !!(ctrl && ctrl.invalid && ctrl.touched);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.saving.set(true);
    const { contractorId, ...offerData } = this.form.value;

    this.contractorService.addServiceOffer(contractorId, this.bidId, offerData).subscribe({
      next: () => this.router.navigate(['/bids', this.bidId]),
      error: () => { this.error.set('Failed to save.'); this.saving.set(false); }
    });
  }
}
