/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addRegistration } from '../fn/registrations/add-registration';
import { AddRegistration$Params } from '../fn/registrations/add-registration';
import { deleteRegistrationById } from '../fn/registrations/delete-registration-by-id';
import { DeleteRegistrationById$Params } from '../fn/registrations/delete-registration-by-id';
import { getAllRegistrations } from '../fn/registrations/get-all-registrations';
import { GetAllRegistrations$Params } from '../fn/registrations/get-all-registrations';
import { getRegistrationById } from '../fn/registrations/get-registration-by-id';
import { GetRegistrationById$Params } from '../fn/registrations/get-registration-by-id';
import { listAllRegistrations } from '../fn/registrations/list-all-registrations';
import { ListAllRegistrations$Params } from '../fn/registrations/list-all-registrations';
import { PageResponseRegistrationResponse } from '../models/page-response-registration-response';
import { RegistrationResponse } from '../models/registration-response';
import { updateRegistration } from '../fn/registrations/update-registration';
import { UpdateRegistration$Params } from '../fn/registrations/update-registration';

@Injectable({ providedIn: 'root' })
export class RegistrationsService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllRegistrations()` */
  static readonly GetAllRegistrationsPath = '/registrations';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllRegistrations()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRegistrations$Response(params?: GetAllRegistrations$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<RegistrationResponse>>> {
    return getAllRegistrations(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllRegistrations$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRegistrations(params?: GetAllRegistrations$Params, context?: HttpContext): Observable<Array<RegistrationResponse>> {
    return this.getAllRegistrations$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<RegistrationResponse>>): Array<RegistrationResponse> => r.body)
    );
  }

  /** Path part for operation `updateRegistration()` */
  static readonly UpdateRegistrationPath = '/registrations';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateRegistration()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRegistration$Response(params: UpdateRegistration$Params, context?: HttpContext): Observable<StrictHttpResponse<RegistrationResponse>> {
    return updateRegistration(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateRegistration$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRegistration(params: UpdateRegistration$Params, context?: HttpContext): Observable<RegistrationResponse> {
    return this.updateRegistration$Response(params, context).pipe(
      map((r: StrictHttpResponse<RegistrationResponse>): RegistrationResponse => r.body)
    );
  }

  /** Path part for operation `addRegistration()` */
  static readonly AddRegistrationPath = '/registrations';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addRegistration()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addRegistration$Response(params: AddRegistration$Params, context?: HttpContext): Observable<StrictHttpResponse<RegistrationResponse>> {
    return addRegistration(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addRegistration$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addRegistration(params: AddRegistration$Params, context?: HttpContext): Observable<RegistrationResponse> {
    return this.addRegistration$Response(params, context).pipe(
      map((r: StrictHttpResponse<RegistrationResponse>): RegistrationResponse => r.body)
    );
  }

  /** Path part for operation `getRegistrationById()` */
  static readonly GetRegistrationByIdPath = '/registrations/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRegistrationById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRegistrationById$Response(params: GetRegistrationById$Params, context?: HttpContext): Observable<StrictHttpResponse<RegistrationResponse>> {
    return getRegistrationById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getRegistrationById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRegistrationById(params: GetRegistrationById$Params, context?: HttpContext): Observable<RegistrationResponse> {
    return this.getRegistrationById$Response(params, context).pipe(
      map((r: StrictHttpResponse<RegistrationResponse>): RegistrationResponse => r.body)
    );
  }

  /** Path part for operation `deleteRegistrationById()` */
  static readonly DeleteRegistrationByIdPath = '/registrations/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteRegistrationById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRegistrationById$Response(params: DeleteRegistrationById$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return deleteRegistrationById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteRegistrationById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRegistrationById(params: DeleteRegistrationById$Params, context?: HttpContext): Observable<void> {
    return this.deleteRegistrationById$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `listAllRegistrations()` */
  static readonly ListAllRegistrationsPath = '/registrations/by-page';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listAllRegistrations()` instead.
   *
   * This method doesn't expect any request body.
   */
  listAllRegistrations$Response(params?: ListAllRegistrations$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseRegistrationResponse>> {
    return listAllRegistrations(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `listAllRegistrations$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listAllRegistrations(params?: ListAllRegistrations$Params, context?: HttpContext): Observable<PageResponseRegistrationResponse> {
    return this.listAllRegistrations$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseRegistrationResponse>): PageResponseRegistrationResponse => r.body)
    );
  }

}
