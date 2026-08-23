import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { QueryHistory } from '../models/query-history.model';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/history';

  getHistory(): Observable<QueryHistory[]> {
    return this.http.get<QueryHistory[]>(this.apiUrl);
  }
}