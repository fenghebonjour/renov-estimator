import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
})
export class NavComponent {
  // protected so the template can read it; injected instead of constructor param.
  protected authService = inject(AuthService);
}
