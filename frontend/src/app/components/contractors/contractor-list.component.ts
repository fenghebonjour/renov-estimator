import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contractor } from '../../models/contractor.model';
import { ContractorService } from '../../services/contractor.service';

@Component({
  selector: 'app-contractor-list',
  templateUrl: './contractor-list.component.html',
  styleUrls: ['./contractor-list.component.css']
})
export class ContractorListComponent implements OnInit {
  contractors: Contractor[] = [];
  loading = true;
  error = '';

  constructor(private service: ContractorService, private router: Router) {}

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: data => { this.contractors = data; this.loading = false; },
      error: () => { this.error = 'Failed to load contractors.'; this.loading = false; }
    });
  }

  goTo(id: number): void {
    this.router.navigate(['/contractors', id]);
  }

  displayName(c: Contractor): string {
    return c.firstName ? `${c.firstName} ${c.lastName}` : (c.name || c.username);
  }
}
