import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import {
  heroHome,
  heroSparkles,
  heroArchiveBox,
  heroUserCircle
} from '@ng-icons/heroicons/outline';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NgIconComponent],
  providers: [provideIcons({ heroHome, heroSparkles, heroArchiveBox, heroUserCircle })],
  template: `
    <div class="min-h-screen bg-gradient-to-br from-amber-50 via-rose-50 to-amber-100 text-stone-900 flex flex-col">
      <header class="px-4 py-3 flex items-center justify-between">
        <div class="text-lg font-semibold tracking-tight">coolinaa</div>
        <a routerLink="/recipes/create" class="text-sm px-3 py-1 rounded-full bg-amber-600 text-white font-semibold shadow-lg shadow-amber-200/60">
          Добавить рецепт
        </a>
      </header>

      <main class="flex-1 px-4 pb-16">
        <router-outlet></router-outlet>
      </main>

      <nav class="fixed bottom-3 inset-x-3 bg-white/90 backdrop-blur rounded-2xl shadow-xl shadow-amber-200/70 border border-stone-200 px-4 py-2 flex justify-between text-sm">
        <a routerLink="/feed" routerLinkActive="text-amber-700" class="py-2 flex flex-col items-center gap-1 text-stone-600">
          <ng-icon name="heroHome" size="20"></ng-icon>
          <span class="sr-only">Лента</span>
        </a>
        <a routerLink="/match" routerLinkActive="text-amber-700" class="py-2 flex flex-col items-center gap-1 text-stone-600">
          <ng-icon name="heroSparkles" size="20"></ng-icon>
          <span class="sr-only">Подбор</span>
        </a>
        <a routerLink="/fridge" routerLinkActive="text-amber-700" class="py-2 flex flex-col items-center gap-1 text-stone-600">
          <ng-icon name="heroArchiveBox" size="20"></ng-icon>
          <span class="sr-only">Холодильник</span>
        </a>
        <a routerLink="/profile" routerLinkActive="text-amber-700" class="py-2 flex flex-col items-center gap-1 text-stone-600">
          <ng-icon name="heroUserCircle" size="20"></ng-icon>
          <span class="sr-only">Профиль</span>
        </a>
      </nav>
    </div>
  `
})
export class MainLayoutComponent {}
