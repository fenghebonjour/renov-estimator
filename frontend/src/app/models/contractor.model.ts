import { ServiceOffer } from './service-offer.model';

export interface Contractor {
  id: number;
  username: string;
  password: string;
  registrationDate: string;
  type: string;
  rating: number;
  yearsOfExperience: number;
  specialty: string;
  phone: string;
  email: string;
  serviceOffers: ServiceOffer[];
  lastName?: string;
  firstName?: string;
  certification?: string;
  name?: string;
  contactPerson?: string;
}
