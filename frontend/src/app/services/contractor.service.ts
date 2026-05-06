import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contractor } from '../models/contractor.model';
import { ServiceOffer } from '../models/service-offer.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ContractorService {
  private base = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Contractor[]> {
    return this.http.get<Contractor[]>(`${this.base}/contractor/all`);
  }

  getById(id: number): Observable<Contractor> {
    return this.http.get<Contractor>(`${this.base}/contractor/find/${id}`);
  }

  createIndividual(data: object): Observable<Contractor> {
    return this.http.post<Contractor>(`${this.base}/individual/add`, data);
  }

  createCompany(data: object): Observable<Contractor> {
    return this.http.post<Contractor>(`${this.base}/company/add`, data);
  }

  addServiceOffer(contractorId: number, bidId: number, offer: Partial<ServiceOffer>): Observable<Contractor> {
    return this.http.put<Contractor>(
      `${this.base}/contractor/addServiceOffer/${contractorId}/${bidId}`,
      offer
    );
  }
}
