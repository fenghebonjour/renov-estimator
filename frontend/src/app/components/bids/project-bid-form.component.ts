import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-project-bid-form',
  templateUrl: './project-bid-form.component.html',
  styleUrls: ['./project-bid-form.component.css']
})
export class ProjectBidFormComponent implements OnInit {
  form!: FormGroup;
  clientId!: number;
  clientName = '';
  saving = false;
  error = '';

  readonly types = ['Painting', 'Plastering', 'Framing', 'Electrical', 'Plumbing', 'Insulation', 'Roofing', 'Other'];

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clientId = Number(this.route.snapshot.paramMap.get('clientId'));
    this.form = this.fb.group({
      type:          ['', Validators.required],
      deadline:      ['', Validators.required],
      workStartDate: ['', Validators.required],
      workEndDate:   ['', Validators.required],
    });

    this.clientService.getById(this.clientId).subscribe({
      next: c => this.clientName = `${c.firstName} ${c.lastName}`,
      error: () => this.error = 'Client not found.'
    });
  }

  isInvalid(field: string): boolean {
    const ctrl = this.form.get(field);
    return !!(ctrl && ctrl.invalid && ctrl.touched);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.saving = true;
    const today = new Date().toISOString().split('T')[0];
    const payload = { ...this.form.value, requestDate: today, status: 'Open' };

    this.clientService.addProjectBid(this.clientId, payload).subscribe({
      next: () => this.router.navigate(['/clients', this.clientId]),
      error: () => { this.error = 'Failed to save.'; this.saving = false; }
    });
  }
}
