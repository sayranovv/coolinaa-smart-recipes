import { inject, Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Category } from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class RecipeCategoryService {
  private readonly api = inject(ApiService);

  list() {
    return this.api.get<Category[]>('/recipe-categories');
  }
}
