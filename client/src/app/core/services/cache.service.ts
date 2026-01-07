import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';

interface CacheEntry<T> {
  data: T;
  timestamp: number;
}

@Injectable({ providedIn: 'root' })
export class CacheService {
  private cache = new Map<string, CacheEntry<any>>();
  
  // Default TTL: 5 minutes
  private readonly DEFAULT_TTL_MS = 5 * 60 * 1000;

  /**
   * Get cached value or execute and cache the observable
   */
  get<T>(key: string, source$: Observable<T>, ttlMs = this.DEFAULT_TTL_MS): Observable<T> {
    const cached = this.getCached<T>(key);
    
    if (cached) {
      return of(cached);
    }

    return source$.pipe(
      tap((data) => this.set(key, data, ttlMs))
    );
  }

  /**
   * Get cached value if exists and not expired
   */
  private getCached<T>(key: string): T | null {
    const entry = this.cache.get(key);
    
    if (!entry) {
      return null;
    }

    // Check if expired
    const isExpired = Date.now() - entry.timestamp > (entry as any).ttl || 0;
    
    if (isExpired) {
      this.cache.delete(key);
      return null;
    }

    return entry.data as T;
  }

  /**
   * Set cache entry
   */
  set<T>(key: string, data: T, ttlMs = this.DEFAULT_TTL_MS): void {
    this.cache.set(key, {
      data,
      timestamp: Date.now(),
      ttl: ttlMs
    } as any);
  }

  /**
   * Clear specific cache key
   */
  invalidate(key: string): void {
    this.cache.delete(key);
  }

  /**
   * Invalidate multiple keys by pattern
   */
  invalidateByPattern(pattern: string): void {
    const regex = new RegExp(pattern);
    for (const key of this.cache.keys()) {
      if (regex.test(key)) {
        this.cache.delete(key);
      }
    }
  }

  /**
   * Clear all cache
   */
  clear(): void {
    this.cache.clear();
  }
}
