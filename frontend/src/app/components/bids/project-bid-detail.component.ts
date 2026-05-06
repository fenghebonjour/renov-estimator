import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectBid } from '../../models/project-bid.model';
import { ServiceOffer } from '../../models/service-offer.model';
import { ProjectBidService } from '../../services/project-bid.service';

@Component({
  selector: 'app-project-bid-detail',
  templateUrl: './project-bid-detail.component.html',
  styleUrls: ['./project-bid-detail.component.css']
})
export class ProjectBidDetailComponent implements OnInit {
  bid: ProjectBid | null = null;
  loading = true;
  error = '';

  constructor(private service: ProjectBidService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.service.getById(id).subscribe({
      next: data => { this.bid = data; this.loading = false; },
      error: () => { this.error = 'Project bid not found.'; this.loading = false; }
    });
  }

  statusClass(status: string): string {
    const s = status?.toLowerCase();
    if (s === 'open') return 'badge-green';
    if (s === 'closed') return 'badge-red';
    return 'badge-blue';
  }

  totalMaterials(offer: ServiceOffer): number {
    return (offer.materials || []).reduce((sum, m) => sum + m.quantity * m.unitPrice, 0);
  }

  totalLabor(offer: ServiceOffer): number {
    return (offer.laborItems || []).reduce((sum, m) => sum + m.quantity * m.unitPrice, 0);
  }
}
