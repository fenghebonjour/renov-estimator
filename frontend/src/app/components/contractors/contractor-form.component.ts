import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ContractorService } from '../../services/contractor.service';

@Component({
  selector: 'app-contractor-form',
  templateUrl: './contractor-form.component.html',
  styleUrls: ['./contractor-form.component.css']
})
export class ContractorFormComponent implements OnInit {
  form!: FormGroup;
  contractorType: 'Individual' | 'Company' = 'Individual';
  saving = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private service: ContractorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm(): void {
    const base = {
      username:         ['', Validators.required],
      password:         ['', Validators.required],
      email:            ['', [Validators.required, Validators.email]],
      phone:            ['', Validators.required],
      specialty:        ['', Validators.required],
      yearsOfExperience:[0, [Validators.required, Validators.min(0)]],
      rating:           [0, [Validators.required, Validators.min(0), Validators.max(10)]],
    };

    if (this.contractorType === 'Individual') {
      this.form = this.fb.group({
        ...base,
        lastName:      ['', Validators.required],
        firstName:     ['', Validators.required],
        certification: [''],
      });
    } else {
      this.form = this.fb.group({
        ...base,
        name:          ['', Validators.required],
        contactPerson: [''],
      });
    }
  }

  setType(type: 'Individual' | 'Company'): void {
    this.contractorType = type;
    this.buildForm();
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
    const payload = {
      ...this.form.value,
      type: this.contractorType,
      registrationDate: today,
    };

    const req$ = this.contractorType === 'Individual'
      ? this.service.createIndividual(payload)
      : this.service.createCompany(payload);

    req$.subscribe({
      next: c => this.router.navigate(['/contractors', c.id]),
      error: () => { this.error = 'Failed to save.'; this.saving = false; }
    });
  }
}
