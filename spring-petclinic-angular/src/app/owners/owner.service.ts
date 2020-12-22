/*
 *
 *  * Copyright 2016-2017 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

/**
 * @author Vitaliy Fedoriv
 */

import {Injectable} from '@angular/core';
import {Owner} from './owner';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {HandleError, HttpErrorHandler} from '../error.service';


@Injectable()
export class OwnerService {

  entityUrl = environment.REST_API_URL + 'customer/owners';

  private readonly handlerError: HandleError;

  constructor(private http: HttpClient, private httpErrorHandler: HttpErrorHandler) {
    this.handlerError = httpErrorHandler.createHandleError('OwnerService');
  }

  getOwners(): Observable<Owner[]> {
    console.log('list owners');
    return this.http.get<Owner[]>(this.entityUrl)
    // return this.http.get<Owner[]>('http://localhost:8080/api/customer/owners')
      .pipe(
        catchError(this.handlerError('getOwners', []))
      );
  }

  // Going via the api gateway to demonstrate the circuit breaker API
  getOwnerById(ownerId: string): Observable<Owner> {
    console.log('list owner by id', ownerId);
    // return this.http.get<Owner>(this.entityUrl + '/' + ownerId)
    return this.http.get<Owner>(environment.REST_API_URL + 'gateway/owners/' + ownerId)
      .pipe(
          catchError(this.handlerError('getOwnerById', {} as Owner))
      );
  }

  addOwner(owner: Owner): Observable<Owner> {
    return this.http.post<Owner>(this.entityUrl, owner)
      .pipe(
        catchError(this.handlerError('addOwner', owner))
      );
  }

  updateOwner(ownerId: string, owner: Owner): Observable<{}> {
    return this.http.put<Owner>(this.entityUrl + '/' + ownerId, owner)
      .pipe(
        catchError(this.handlerError('updateOwner', owner))
      );
  }

  deleteOwner(ownerId: string): Observable<{}> {
    return this.http.delete<Owner>(this.entityUrl + '/' + ownerId)
      .pipe(
         catchError(this.handlerError('deleteOwner', [ownerId]))
      );
  }


}
