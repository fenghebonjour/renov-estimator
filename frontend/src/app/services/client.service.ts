import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';
import { ProjectBid } from '../models/project-bid.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private base = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.base}/client/all`);
  }

  getById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.base}/client/find/${id}`);
  }

  create(client: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(`${this.base}/client/add`, client);
  }

  update(client: Partial<Client>): Observable<Client> {
    return this.http.put<Client>(`${this.base}/client/update`, client);
  }

  addProjectBid(clientId: number, bid: Partial<ProjectBid>): Observable<Client> {
    return this.http.put<Client>(`${this.base}/client/addProjectBid/${clientId}`, bid);
  }
}
