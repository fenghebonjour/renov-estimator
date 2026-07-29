import { ProjectBid } from './project-bid.model';

export interface Address {
  id: number;
  streetNumber: string;
  unit: string;
  street: string;
  city: string;
  province: string;
  country: string;
  postalCode: string;
}

export interface UserAddress {
  id: { userId: number; addressId: number };
  address: Address;
  addressType: string;
}

export interface Client {
  id: number;
  username: string;
  password: string;
  registrationDate: string;
  type: string;
  lastName: string;
  firstName: string;
  email: string;
  phone: string;
  addresses: UserAddress[];
  projectBids: ProjectBid[];
}
