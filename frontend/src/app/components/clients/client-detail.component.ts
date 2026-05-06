import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {
  client: Client | null = null;
  loading = true;
  error = '';

  constructor(
    private service: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.service.getById(id).subscribe({
      next: data => { this.client = data; this.loading = false; },
      error: () => { this.error = 'Client not found.'; this.loading = false; }
    });
  }

  goToBid(id: number): void {
    this.router.navigate(['/bids', id]);
  }

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }
}
