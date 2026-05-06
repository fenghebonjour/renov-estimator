import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjectBid } from '../models/project-bid.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProjectBidService {
  private base = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getAll(): Observable<ProjectBid[]> {
    return this.http.get<ProjectBid[]>(`${this.base}/project-bid/all`);
  }

  getById(id: number): Observable<ProjectBid> {
    return this.http.get<ProjectBid>(`${this.base}/project-bid/find/${id}`);
  }
}
