import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectBid } from '../../models/project-bid.model';
import { ProjectBidService } from '../../services/project-bid.service';

@Component({
  selector: 'app-project-bid-list',
  templateUrl: './project-bid-list.component.html',
  styleUrls: ['./project-bid-list.component.css']
})
export class ProjectBidListComponent implements OnInit {
  bids: ProjectBid[] = [];
  loading = true;
  error = '';

  constructor(private service: ProjectBidService, private router: Router) {}

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: data => { this.bids = data; this.loading = false; },
      error: () => { this.error = 'Failed to load project bids.'; this.loading = false; }
    });
  }

  goTo(id: number): void {
    this.router.navigate(['/bids', id]);
  }

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }
}
