import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[] = [];
  loading = true;
  error = '';

  constructor(private service: ClientService, private router: Router) {}

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: data => { this.clients = data; this.loading = false; },
      error: () => { this.error = 'Failed to load clients.'; this.loading = false; }
    });
  }

  goTo(id: number): void {
    this.router.navigate(['/clients', id]);
  }
}
