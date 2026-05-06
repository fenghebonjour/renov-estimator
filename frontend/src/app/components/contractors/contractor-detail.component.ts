import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Contractor } from '../../models/contractor.model';
import { ContractorService } from '../../services/contractor.service';

@Component({
  selector: 'app-contractor-detail',
  templateUrl: './contractor-detail.component.html',
  styleUrls: ['./contractor-detail.component.css']
})
export class ContractorDetailComponent implements OnInit {
  contractor: Contractor | null = null;
  loading = true;
  error = '';

  constructor(private service: ContractorService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.service.getById(id).subscribe({
      next: data => { this.contractor = data; this.loading = false; },
      error: () => { this.error = 'Contractor not found.'; this.loading = false; }
    });
  }

  displayName(c: Contractor): string {
    return c.firstName ? `${c.firstName} ${c.lastName}` : (c.name || c.username);
  }

  isIndividual(c: Contractor): boolean {
    return c.type?.toLowerCase() === 'individual';
  }
}
