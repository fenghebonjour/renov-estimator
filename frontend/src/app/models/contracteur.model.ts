import { OffreService } from './offre-service.model';

export interface Contracteur {
  id: number;
  username: string;
  password: string;
  dateInscription: string;
  type: string;
  note: number;
  anneeExperience: number;
  activite: string;
  telephone: string;
  courriel: string;
  offreServices: OffreService[];
  nom?: string;
  prenom?: string;
  certificat?: string;
  contact?: string;
}
