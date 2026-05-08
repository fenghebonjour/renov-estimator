import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/bids', pathMatch: 'full' },
  { path: 'bids',                               component: ProjectBidListComponent,   canActivate: [AuthGuard] },
  { path: 'bids/:id',                           component: ProjectBidDetailComponent,  canActivate: [AuthGuard] },
  { path: 'bids/:bidId/offers/new',             component: ServiceOfferFormComponent,  canActivate: [AuthGuard] },
  { path: 'clients',                            component: ClientListComponent,        canActivate: [AuthGuard] },
  { path: 'clients/new',                        component: ClientFormComponent,        canActivate: [AuthGuard] },
  { path: 'clients/:id',                        component: ClientDetailComponent,      canActivate: [AuthGuard] },
  { path: 'clients/:id/edit',                   component: ClientFormComponent,        canActivate: [AuthGuard] },
  { path: 'clients/:clientId/bids/new',         component: ProjectBidFormComponent,    canActivate: [AuthGuard] },
  { path: 'contractors',                        component: ContractorListComponent,    canActivate: [AuthGuard] },
  { path: 'contractors/new',                    component: ContractorFormComponent,    canActivate: [AuthGuard] },
  { path: 'contractors/:id',                    component: ContractorDetailComponent,  canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
