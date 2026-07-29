import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjectBid } from '../models/project-bid.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProjectBidService {
  private http = inject(HttpClient);
  private base = environment.apiUrl;

  getAll(): Observable<ProjectBid[]> {
    return this.http.get<ProjectBid[]>(`${this.base}/project-bid/all`);
  }

  getById(id: number): Observable<ProjectBid> {
    return this.http.get<ProjectBid>(`${this.base}/project-bid/find/${id}`);
  }
}
