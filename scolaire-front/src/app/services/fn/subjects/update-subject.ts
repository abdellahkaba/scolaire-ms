/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { SubjectRequest } from '../../models/subject-request';
import { SubjectResponse } from '../../models/subject-response';

export interface UpdateSubject$Params {
      body: SubjectRequest
}

export function updateSubject(http: HttpClient, rootUrl: string, params: UpdateSubject$Params, context?: HttpContext): Observable<StrictHttpResponse<SubjectResponse>> {
  const rb = new RequestBuilder(rootUrl, updateSubject.PATH, 'put');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<SubjectResponse>;
    })
  );
}

updateSubject.PATH = '/subjects';
