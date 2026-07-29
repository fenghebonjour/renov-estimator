import { Routes } from '@angular/router';
import { ProjectBidListComponent } from './components/bids/project-bid-list.component';
import { ProjectBidDetailComponent } from './components/bids/project-bid-detail.component';
import { ProjectBidFormComponent } from './components/bids/project-bid-form.component';
import { ServiceOfferFormComponent } from './components/bids/service-offer-form.component';
import { ClientListComponent } from './components/clients/client-list.component';
import { ClientDetailComponent } from './components/clients/client-detail.component';
import { ClientFormComponent } from './components/clients/client-form.component';
import { ContractorListComponent } from './components/contractors/contractor-list.component';
import { ContractorDetailComponent } from './components/contractors/contractor-detail.component';
import { ContractorFormComponent } from './components/contractors/contractor-form.component';
import { LoginComponent } from './components/auth/login.component';
import { LaboDashboardComponent } from './components/labo/labo-dashboard.component';
import { JvmMetricsComponent } from './components/labo/jvm-metrics.component';
import { authGuard } from './guards/auth.guard';

// Standalone routing: routes reference the components directly and are provided
// via provideRouter() in app.config.ts — no NgModule / RouterModule.forRoot().
export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/bids', pathMatch: 'full' },
  { path: 'bids',                       component: ProjectBidListComponent,   canActivate: [authGuard] },
  { path: 'bids/:id',                   component: ProjectBidDetailComponent, canActivate: [authGuard] },
  { path: 'bids/:bidId/offers/new',     component: ServiceOfferFormComponent, canActivate: [authGuard] },
  { path: 'clients',                    component: ClientListComponent,       canActivate: [authGuard] },
  { path: 'clients/new',                component: ClientFormComponent,       canActivate: [authGuard] },
  { path: 'clients/:id',                component: ClientDetailComponent,     canActivate: [authGuard] },
  { path: 'clients/:id/edit',           component: ClientFormComponent,       canActivate: [authGuard] },
  { path: 'clients/:clientId/bids/new', component: ProjectBidFormComponent,   canActivate: [authGuard] },
  { path: 'contractors',                component: ContractorListComponent,   canActivate: [authGuard] },
  { path: 'contractors/new',            component: ContractorFormComponent,   canActivate: [authGuard] },
  { path: 'contractors/:id',            component: ContractorDetailComponent, canActivate: [authGuard] },
  { path: 'labo',                       component: LaboDashboardComponent,    canActivate: [authGuard] },
  { path: 'labo/jvm-metrics',           component: JvmMetricsComponent,       canActivate: [authGuard] },
];
