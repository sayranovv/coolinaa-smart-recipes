import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

const API_BASE = '/api/v1';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);

  private url(path: string): string {
    return `${API_BASE}${path}`;
  }

  get<T>(path: string, options: { params?: Record<string, any> } = {}) {
    const cleaned = options.params
      ? Object.fromEntries(
          Object.entries(options.params).filter(([, value]) => value !== undefined && value !== null)
        )
      : undefined;

    return this.http.get<T>(this.url(path), cleaned ? { ...options, params: cleaned } : options);
  }

  post<T>(path: string, body: unknown, headers?: HttpHeaders) {
    return this.http.post<T>(this.url(path), body, { headers });
  }

  put<T>(path: string, body: unknown) {
    return this.http.put<T>(this.url(path), body);
  }

  patch<T>(path: string, body: unknown) {
    return this.http.patch<T>(this.url(path), body);
  }

  delete<T>(path: string) {
    return this.http.delete<T>(this.url(path));
  }
}
