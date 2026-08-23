import {
  ChangeDetectorRef,
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { HistoryService } from '../../services/history.service';
import { QueryHistory } from '../../models/query-history.model';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {

  private readonly historyService = inject(HistoryService);
  private readonly cdr = inject(ChangeDetectorRef);

  history: QueryHistory[] = [];
  loading = true;
  error = '';

  ngOnInit(): void {
    this.loadHistory();
  }

  loadHistory(): void {

    this.loading = true;
    this.error = '';

    this.historyService.getHistory().subscribe({

      next: (data) => {
        this.history = data;
        this.loading = false;

        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error(error);

        this.error = 'Impossible de charger l’historique.';
        this.loading = false;

        this.cdr.detectChanges();
      }

    });
  }

  
  refresh(): void {
    this.loadHistory();
  }
}