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

const routes: Routes = [
  { path: '', redirectTo: '/bids', pathMatch: 'full' },
  { path: 'bids',                               component: ProjectBidListComponent },
  { path: 'bids/:id',                           component: ProjectBidDetailComponent },
  { path: 'bids/:bidId/offers/new',             component: ServiceOfferFormComponent },
  { path: 'clients',                            component: ClientListComponent },
  { path: 'clients/new',                        component: ClientFormComponent },
  { path: 'clients/:id',                        component: ClientDetailComponent },
  { path: 'clients/:id/edit',                   component: ClientFormComponent },
  { path: 'clients/:clientId/bids/new',         component: ProjectBidFormComponent },
  { path: 'contractors',                        component: ContractorListComponent },
  { path: 'contractors/new',                    component: ContractorFormComponent },
  { path: 'contractors/:id',                    component: ContractorDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
