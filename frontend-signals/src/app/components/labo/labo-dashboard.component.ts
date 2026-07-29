import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-labo-dashboard',
  templateUrl: './labo-dashboard.component.html',
  styleUrls: ['./labo-dashboard.component.css'],
  standalone: true,
  imports: [RouterLink],
})
export class LaboDashboardComponent {}
