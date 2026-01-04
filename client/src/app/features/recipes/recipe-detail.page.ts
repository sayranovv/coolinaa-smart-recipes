import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { RecipeService } from '../../core/services/recipe.service';
import { Recipe } from '../../core/models/recipe.model';

@Component({
  selector: 'app-recipe-detail-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <ng-container *ngIf="recipe; else missing">
      <section class="space-y-4">
        <a routerLink="/recipes" class="text-sm text-accent-800">Назад к списку</a>
        <div class="space-y-2">
          <p class="text-xs uppercase tracking-wide text-stone-500">{{ recipe.categoryName || 'Без категории' }}</p>
          <h1 class="text-3xl font-semibold text-stone-900">{{ recipe.title }}</h1>
          <p class="text-stone-700">{{ recipe.description || 'Описание скоро появится' }}</p>
        </div>
        <div class="flex flex-wrap gap-2 text-xs text-stone-600">
          <span class="px-2 py-1 rounded-full bg-stone-100 border border-stone-200" *ngIf="recipe.averageRating">
            ★ {{ recipe.averageRating | number: '1.1-1' }}
          </span>
          <span class="px-2 py-1 rounded-full bg-stone-100 border border-stone-200" *ngIf="recipe.cookingTime">
            {{ recipe.cookingTime }} мин
          </span>
          <span class="px-2 py-1 rounded-full bg-stone-100 border border-stone-200" *ngIf="recipe.servings">
            {{ recipe.servings }} порций
          </span>
        </div>
        <div class="rounded-2xl border border-stone-200 bg-white/90 p-4 space-y-2 text-sm text-stone-800">
          <p class="font-semibold">Инструкции</p>
          <p class="whitespace-pre-line leading-relaxed">{{ recipe.instructions }}</p>
        </div>
        <div class="rounded-2xl border border-stone-200 bg-white/90 p-4 space-y-2 text-sm text-stone-800" *ngIf="recipe.ingredients?.length">
          <p class="font-semibold">Ингредиенты</p>
          <ul class="space-y-1">
            <li *ngFor="let ing of recipe.ingredients">
              {{ ing.ingredientName || 'Ингредиент' }} — {{ ing.quantity || '?' }} {{ ing.unitName || '' }} {{ ing.notes || '' }}
            </li>
          </ul>
        </div>
      </section>
    </ng-container>
    <ng-template #missing>
      <section class="space-y-3">
        <p class="text-lg font-semibold">Рецепт не найден</p>
        <a routerLink="/recipes" class="text-accent-800 text-sm">Вернуться к списку</a>
      </section>
    </ng-template>
  `
})
export class RecipeDetailPage implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly recipes = inject(RecipeService);

  protected recipe: Recipe | null = null;
  protected error = '';

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.recipes.get(id).subscribe({
        next: (res) => (this.recipe = res),
        error: () => (this.error = 'Не удалось загрузить рецепт')
      });
    } else {
      this.error = 'Рецепт не найден';
    }
  }
}
