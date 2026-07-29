import { Component, OnInit, inject, signal } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client-form',
  templateUrl: './client-form.component.html',
  styleUrls: ['./client-form.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
})
export class ClientFormComponent implements OnInit {
  private fb = inject(UntypedFormBuilder);
  private service = inject(ClientService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  form!: UntypedFormGroup;
  editId: number | null = null;
  saving = signal(false);
  error = signal('');

  ngOnInit(): void {
    this.form = this.fb.group({
      username:  ['', Validators.required],
      password:  ['', Validators.required],
      lastName:  ['', Validators.required],
      firstName: ['', Validators.required],
      email:     ['', [Validators.required, Validators.email]],
      phone:     ['', Validators.required],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editId = Number(id);
      this.service.getById(this.editId).subscribe({
        next: c => this.form.patchValue({
          username: c.username, password: c.password,
          lastName: c.lastName, firstName: c.firstName,
          email: c.email, phone: c.phone,
        }),
        error: () => this.error.set('Client not found.')
      });
    }
  }

  get isEdit(): boolean { return this.editId !== null; }

  isInvalid(field: string): boolean {
    const ctrl = this.form.get(field);
    return !!(ctrl && ctrl.invalid && ctrl.touched);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;

    this.saving.set(true);
    const payload = {
      ...this.form.value,
      type: 'client',
      registrationDate: new Date().toISOString().split('T')[0],
      ...(this.editId ? { id: this.editId } : {})
    };

    const req$ = this.isEdit
      ? this.service.update(payload)
      : this.service.create(payload);

    req$.subscribe({
      next: c => this.router.navigate(['/clients', c.id]),
      error: () => { this.error.set('Failed to save.'); this.saving.set(false); }
    });
  }
}
