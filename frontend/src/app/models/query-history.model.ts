export interface QueryHistory {
  id: number;
  question: string;
  generatedSql: string;
  status: string;
  rowCount: number;
  executionTimeMs: number;
  createdAt: string;
}