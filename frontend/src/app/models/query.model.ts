export interface QueryRequest {
  question: string;
}

export interface QueryResponse {
  question: string;
  sql: string;
  rows: Record<string, unknown>[];
}