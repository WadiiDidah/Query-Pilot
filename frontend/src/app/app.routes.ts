import { Routes } from '@angular/router';

import {
  QueryPageComponent
} from './pages/query-page/query-page.component';

export const routes: Routes = [
  {
    path: '',
    component: QueryPageComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];