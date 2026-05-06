import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ContractorService } from '../../services/contractor.service';
import { Contractor } from '../../models/contractor.model';

@Component({
  selector: 'app-service-offer-form',
  templateUrl: './service-offer-form.component.html',
  styleUrls: ['./service-offer-form.component.css']
})
export class ServiceOfferFormComponent implements OnInit {
  form!: FormGroup;
  bidId!: number;
  contractors: Contractor[] = [];
  loadingContractors = true;
  saving = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private contractorService: ContractorService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

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
      next: data => { this.contractors = data; this.loadingContractors = false; },
      error: () => { this.error = 'Failed to load contractors.'; this.loadingContractors = false; }
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

    this.saving = true;
    const { contractorId, ...offerData } = this.form.value;

    this.contractorService.addServiceOffer(contractorId, this.bidId, offerData).subscribe({
      next: () => this.router.navigate(['/bids', this.bidId]),
      error: () => { this.error = 'Failed to save.'; this.saving = false; }
    });
  }
}
