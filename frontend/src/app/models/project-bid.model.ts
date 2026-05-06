import { ServiceOffer } from './service-offer.model';

export interface ProjectBid {
  id: number;
  requestDate: string;
  deadline: string;
  workStartDate: string;
  workEndDate: string;
  status: string;
  type: string;
  serviceOffers: ServiceOffer[];
}
