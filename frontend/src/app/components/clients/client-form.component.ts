import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client-form',
  templateUrl: './client-form.component.html',
  styleUrls: ['./client-form.component.css']
})
export class ClientFormComponent implements OnInit {
  form!: FormGroup;
  editId: number | null = null;
  saving = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private service: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

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
        error: () => this.error = 'Client not found.'
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

    this.saving = true;
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
      error: () => { this.error = 'Failed to save.'; this.saving = false; }
    });
  }
}
