import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  QueryRequest,
  QueryResponse
} from '../models/query.model';

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/queries';

  ask(question: string): Observable<QueryResponse> {

    const body: QueryRequest = {
      question
    };

    return this.http.post<QueryResponse>(
      this.apiUrl,
      body
    );
  }
}