export interface Material {
  id: number;
  description: string;
  unitPrice: number;
}

export interface Labor {
  id: number;
  description: string;
  grade: number;
  hourlyRate: number;
}

export interface BidMaterial {
  material: Material;
  quantity: number;
  unitPrice: number;
}

export interface BidLabor {
  labor: Labor;
  quantity: number;
  unitPrice: number;
}

export interface ServiceOffer {
  id: number;
  offerDate: string;
  validUntil: string;
  status: string;
  amount: number;
  materials: BidMaterial[];
  laborItems: BidLabor[];
}
