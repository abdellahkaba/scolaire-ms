/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AcademieYearRequest } from '../../models/academie-year-request';
import { AcademieYearResponse } from '../../models/academie-year-response';

export interface UpdateAcademieYear$Params {
      body: AcademieYearRequest
}

export function updateAcademieYear(http: HttpClient, rootUrl: string, params: UpdateAcademieYear$Params, context?: HttpContext): Observable<StrictHttpResponse<AcademieYearResponse>> {
  const rb = new RequestBuilder(rootUrl, updateAcademieYear.PATH, 'put');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AcademieYearResponse>;
    })
  );
}

updateAcademieYear.PATH = '/academieYears';
