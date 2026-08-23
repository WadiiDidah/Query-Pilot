import {
  ChangeDetectorRef,
  Component,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { QueryService } from '../../services/query.service';
import { QueryResponse } from '../../models/query.model';

@Component({
  selector: 'app-query-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './query-page.component.html',
  styleUrl: './query-page.component.css'
})
export class QueryPageComponent {

  private readonly queryService = inject(QueryService);
  private readonly cdr = inject(ChangeDetectorRef);

  question = '';
  loading = false;
  error = '';
  result?: QueryResponse;

  submit(): void {

    const question = this.question.trim();

    if (!question) {
      this.error = 'Merci de saisir une question.';
      return;
    }

    if (this.loading) {
      return;
    }

    this.loading = true;
    this.error = '';
    this.result = undefined;

    this.queryService
      .ask(question)
      .subscribe({

        next: response => {

          if (response) {

            this.result = response;
            this.loading = false;

          } else {

            this.error =
              'Aucun résultat reçu.';

            this.loading = false;
          }

          this.cdr.detectChanges();
        },

        error: error => {

          if (error?.error?.message) {

            this.error =
              error.error.message;

          } else {

            this.error =
              'Impossible de traiter la requête.';
          }

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }

  useExample(question: string): void {

    if (question) {
      this.question = question;
    }
  }

  get columns(): string[] {

    if (!this.result) {
      return [];
    }

    if (!this.result.rows) {
      return [];
    }

    if (this.result.rows.length === 0) {
      return [];
    }

    return Object.keys(
      this.result.rows[0]
    );
  }

  newQuery(): void {

    this.question = '';
    this.result = undefined;
    this.error = '';
    this.loading = false;
  }

  copySql(): void {

    if (!this.result) {
      return;
    }

    if (!this.result.sql) {
      return;
    }

    navigator.clipboard.writeText(
      this.result.sql
    );
  }
}