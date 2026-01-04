import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserIngredientService } from '../../core/services/user-ingredient.service';
import { IngredientService } from '../../core/services/ingredient.service';
import { Ingredient, UserIngredient, UserIngredientRequest } from '../../core/models/ingredient.model';
import { Unit } from '../../core/models/unit.model';

@Component({
  selector: 'app-fridge-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrl: './fridge.page.css',
  template: `
    <section class="fridge-shell space-y-5 mt-4">
      <div class="flex items-center justify-between ">
        <div>
          <p class="text-xs uppercase tracking-wide text-sky-300">🧊 fresh keeper</p>
          <h1 class="text-2xl font-semibold text-white">Мой холодильник</h1>
          <p class="text-sm text-sky-100/90">Следите за запасами и сроками годности.</p>
        </div>
        <div class="flex gap-2 items-center justify-center flex-wrap bg-white/10 px-3 py-2 rounded-xl border border-white/20 shadow-lg">
          <div class="flex flex-col leading-tight text-white/80 text-xs">
            <span>Температура</span>
            <strong class="text-lg text-white">+4°C</strong>
          </div>
          <button class="fridge-button" (click)="add()">Добавить</button>
        </div>
      </div>

      <div class="frost-panel space-y-4">
        <div class="grid gap-3 sm:grid-cols-2">
          <label class="field">
            <span>Ингредиент</span>
            <select class="input" [(ngModel)]="form.ingredientId">
              <option [ngValue]="undefined">Выберите</option>
              <option *ngFor="let item of ingredients" [ngValue]="item.id">{{ item.name }}</option>
            </select>
          </label>

          <label class="field">
            <span>Количество</span>
            <input class="input" type="number" min="0" step="0.1" [(ngModel)]="form.quantity" />
          </label>

          <label class="field">
            <span>Ед. измерения</span>
            <select class="input" [(ngModel)]="form.unitId">
              <option [ngValue]="undefined">Не выбрано</option>
              <option *ngFor="let u of units" [ngValue]="u.id">{{ u.abbreviation || u.name }}</option>
            </select>
          </label>

          <label class="field">
            <span>Срок годности (опционально)</span>
            <input class="input" type="date" [(ngModel)]="form.expiresAt" />
          </label>
        </div>
        <p *ngIf="error" class="text-sm text-red-200">{{ error }}</p>
      </div>

      <div class="frost-panel space-y-3" *ngIf="items.length; else empty">
        <div class="space-y-2">
          <div
            *ngFor="let item of items"
            class="flex items-center justify-between border-b border-white/10 pb-2 last:border-none last:pb-0"
          >
            <div class="leading-tight text-white">
              <p class="font-semibold">{{ item.ingredientName }}</p>
              <p class="text-xs text-sky-100/80">{{ item.quantity || 0 }} {{ item.unitName || '' }}</p>
              <p class="text-xs text-sky-100/60" *ngIf="item.expiresAt">Годен до {{ item.expiresAt | date }}</p>
            </div>
            <button class="text-sm text-rose-100 hover:text-rose-200" (click)="remove(item.ingredientId)">Удалить</button>
          </div>
        </div>
      </div>
      <ng-template #empty>
        <div class="frost-panel text-sm text-sky-100/80">
          Список пуст. Добавьте продукты, чтобы получить рекомендации.
        </div>
      </ng-template>
    </section>
  `
})
export class FridgePage implements OnInit {
  private readonly userIngredients = inject(UserIngredientService);
  private readonly ingredientsApi = inject(IngredientService);

  protected items: UserIngredient[] = [];
  protected ingredients: Ingredient[] = [];
  protected units: Unit[] = [];
  protected error = '';

  protected form: Partial<UserIngredientRequest> = {
    ingredientId: undefined,
    quantity: 0,
    unitId: undefined,
    expiresAt: undefined
  };

  ngOnInit() {
    this.load();
    this.loadDictionaries();
  }

  private load() {
    this.userIngredients.listAll().subscribe({
      next: (res) => (this.items = res),
      error: () => (this.error = 'Не удалось загрузить продукты')
    });
  }

  private loadDictionaries() {
    this.ingredientsApi.list({ size: 50 }).subscribe((res) => (this.ingredients = res.content || []));
    this.ingredientsApi.units().subscribe((res) => (this.units = res));
  }

  add() {
    if (!this.form.ingredientId || !this.form.quantity) {
      this.error = 'Заполните ингредиент и количество';
      return;
    }
    this.error = '';
    this.userIngredients.add({
      ingredientId: this.form.ingredientId,
      quantity: Number(this.form.quantity),
      unitId: this.form.unitId || undefined,
      expiresAt: this.form.expiresAt || undefined
    }).subscribe({
      next: () => {
        this.load();
      },
      error: () => {
        this.error = 'Не удалось сохранить продукт';
      }
    });
  }

  remove(ingredientId: number) {
    this.userIngredients.remove(ingredientId).subscribe({
      next: () => this.load(),
      error: () => (this.error = 'Не удалось удалить продукт')
    });
  }
}
