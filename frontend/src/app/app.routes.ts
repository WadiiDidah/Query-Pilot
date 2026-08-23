import { Routes } from '@angular/router';

import {
  QueryPageComponent
} from './pages/query-page/query-page.component';

import {
  HistoryComponent
} from './pages/history/history.component';

export const routes: Routes = [
  {
    path: '',
    component: QueryPageComponent
  },
  {
    path: 'history',
    component: HistoryComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];