import {Injectable} from '@angular/core';
import Keycloak from 'keycloak-js';
import {Router} from '@angular/router';
import {UserProfile} from '../profile/user-profile';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  constructor(
    private router: Router
  ) {
  }

  get keycloak() {
    if (!this._keycloak){
      this._keycloak = new Keycloak({
        url: 'http://localhost:8181',
        realm: 'scolaire-ms',
        clientId: 'scolaire-ms-app'
      })
    }
    return this._keycloak
  }

  async init() {
    const authenticated = await this.keycloak.init({
      onLoad: 'login-required'
    })

    if (authenticated){
      this._profile = (await this.keycloak?.loadUserProfile()) as UserProfile
      this._profile.token = this.keycloak?.token || ''
    }
  }

  async login(){
    await this.keycloak.login();
  }

  get userId(): string {
    return this.keycloak?.tokenParsed?.sub as string
  }

  get isTokenValid(): boolean {
    return !this.keycloak.isTokenExpired();
  }

  get fullName(): string {
    return this.keycloak.tokenParsed?.['name'] as string
  }
  logout(){
    return this.keycloak.logout({redirectUri: 'http://localhost:4200'});
  }

  accountManagement(){
    return this.keycloak.accountManagement();
  }
}
