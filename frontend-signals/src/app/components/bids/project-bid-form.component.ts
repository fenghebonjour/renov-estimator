import { Component, OnInit, inject, signal } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-project-bid-form',
  templateUrl: './project-bid-form.component.html',
  styleUrls: ['./project-bid-form.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
})
export class ProjectBidFormComponent implements OnInit {
  private fb = inject(UntypedFormBuilder);
  private clientService = inject(ClientService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  form!: UntypedFormGroup;
  clientId!: number;
  clientName = signal('');
  saving = signal(false);
  error = signal('');

  readonly types = ['Painting', 'Plastering', 'Framing', 'Electrical', 'Plumbing', 'Insulation', 'Roofing', 'Other'];

  ngOnInit(): void {
    this.clientId = Number(this.route.snapshot.paramMap.get('clientId'));
    this.form = this.fb.group({
      type:          ['', Validators.required],
      deadline:      ['', Validators.required],
      workStartDate: ['', Validators.required],
      workEndDate:   ['', Validators.required],
    });

    this.clientService.getById(this.clientId).subscribe({
      next: c => this.clientName.set(`${c.firstName} ${c.lastName}`),
      error: () => this.error.set('Client not found.')
    });
  }

  isInvalid(field: string): boolean {
    const ctrl = this.form.get(field);
    return !!(ctrl && ctrl.invalid && ctrl.touched);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.saving.set(true);
    const today = new Date().toISOString().split('T')[0];
    const payload = { ...this.form.value, requestDate: today, status: 'Open' };

    this.clientService.addProjectBid(this.clientId, payload).subscribe({
      next: () => this.router.navigate(['/clients', this.clientId]),
      error: () => { this.error.set('Failed to save.'); this.saving.set(false); }
    });
  }
}
