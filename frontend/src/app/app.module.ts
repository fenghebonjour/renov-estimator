import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './components/nav/nav.component';
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

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    ProjectBidListComponent,
    ProjectBidDetailComponent,
    ProjectBidFormComponent,
    ServiceOfferFormComponent,
    ClientListComponent,
    ClientDetailComponent,
    ClientFormComponent,
    ContractorListComponent,
    ContractorDetailComponent,
    ContractorFormComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
