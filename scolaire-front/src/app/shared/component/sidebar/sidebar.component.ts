import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import { KeycloakService } from '../../../services/utils/keycloak/keycloak.service';

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit{

  constructor(
    private keycloakService: KeycloakService
  ) {
  }
  ngOnInit() {
  }
  async logout() {
    await this.keycloakService.logout();
  }

}
