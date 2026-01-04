import { inject, Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Page } from '../models/page.model';
import { Ingredient } from '../models/ingredient.model';
import { Unit } from '../models/unit.model';

@Injectable({ providedIn: 'root' })
export class IngredientService {
  private readonly api = inject(ApiService);

  list(params: { page?: number; size?: number; categoryId?: number } = {}) {
    return this.api.get<Page<Ingredient>>('/ingredients', { params });
  }

  units() {
    return this.api.get<Unit[]>('/units');
  }
}
