# 🍳 coolinaa — умные рецепты

<div align="center">

![Coolinaa](https://img.shields.io/badge/Coolinaa-Smart_Recipes-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green?style=for-the-badge&logo=springboot)
![Angular](https://img.shields.io/badge/Angular-21-red?style=for-the-badge&logo=angular)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?style=for-the-badge&logo=postgresql)

**Современное веб-приложение для управления рецептами с интеллектуальным подбором блюд по имеющимся продуктам**

[🚀 Открыть Приложение](https://coolinaa-smart-recipes.vercel.app) • [📖 Документация](#-документация) • [🛠 Технологии](#-технологии)

</div>

---

## 📋 О Проекте

**coolinaa** — это веб-платформа для кулинаров и любителей готовить, объединяющая в себе:

🥘 **Каталог рецептов** — создавайте, публикуйте и делитесь своими кулинарными шедеврами  
🧊 **Умный холодильник** — отслеживайте продукты, сроки годности и запасы  
🎯 **Интеллектуальный подбор** — получайте рекомендации рецептов на основе имеющихся ингредиентов  
⭐ **Отзывы и рейтинги** — оценивайте блюда и находите самые популярные рецепты  
👨‍💼 **Админ-панель** — управление справочниками (ингредиенты, категории, единицы измерения)

---

## 🌐 Демо

> **🎉 Самый простой способ попробовать Coolinaa — перейти по ссылке:**
>
> ### **👉 [https://coolinaa-smart-recipes.vercel.app](https://coolinaa-smart-recipes.vercel.app)**
>
> Приложение полностью развёрнуто и готово к использованию! (если не получается войти/зарегистрироваться — попробуйте ещё раз через несколько минут, бэкенд может спать из-за бесплатного хостинга)

---

## 🛠 Технологии

### Backend
- **Java 21** — современная версия JDK с performance-улучшениями
- **Spring Boot 3.5.7** — фреймворк для создания REST API
- **Spring Security 6.5** — JWT-аутентификация, CORS, защита эндпоинтов
- **Spring Data JPA** — ORM для работы с базой данных через Hibernate
- **PostgreSQL 17** — реляционная СУБД с поддержкой JSON, индексов и миграций
- **Flyway** — версионирование схемы базы данных
- **BCrypt** — безопасное хеширование паролей
- **SpringDoc OpenAPI** — автоматическая документация API (Swagger UI)
- **Maven** — сборка и управление зависимостями
- **Docker** — контейнеризация приложения

### Frontend
- **Angular 21** — современный фреймворк для SPA (standalone components)
- **TypeScript** — типизированный JavaScript
- **RxJS** — реактивное программирование и управление состоянием
- **Tailwind CSS** — utility-first CSS фреймворк
- **ng-icons** — иконки для интерфейса
- **Vercel** — платформа для хостинга фронтенда

### Инфраструктура
- **Render** — хостинг бэкенда (Docker)
- **Vercel** — хостинг фронтенда (SPA)
- **Render Postgres** — облачная база данных PostgreSQL

---

## ✨ Функционал

### 🔐 Аутентификация и Авторизация
- Регистрация и вход (JWT токены: access + refresh)
- Разделение ролей: `user`, `admin`
- Защищённые маршруты и API эндпоинты
- Автоматическое обновление токенов

### 📚 Рецепты
- **Лента рецептов** с фильтрацией по категориям и поиском
- **Создание рецепта** с ингредиентами, инструкциями, временем приготовления
- **Детальная страница** с отзывами и оценками
- **Мои рецепты** — управление собственными публикациями
- Категории: Завтрак, Обед, Ужин, Десерты, Супы, Салаты и др.

### 🧊 Мой Холодильник
- Добавление продуктов с количеством и единицами измерения
- Отслеживание **сроков годности** с уведомлениями
- Автоматическое визуальное оформление (градиент, иконки)
- Удаление и редактирование ингредиентов

### 🎯 Интеллектуальный Подбор
- **Алгоритм совпадения:** рецепты, в которых уже есть ваши продукты или не хватает максимум 3 ингредиентов
- Процент совпадения и список недостающих продуктов
- Фильтрация по категориям рецептов
- Сортировка по релевантности

### ⭐ Отзывы и Рейтинги
- Оценка рецептов (1–5 звёзд)
- Текстовые комментарии
- Средний рейтинг и количество отзывов
- Редактирование и удаление собственных отзывов

### 👨‍💼 Админ-Панель
- CRUD операции для **категорий ингредиентов**
- CRUD операции для **ингредиентов** (с привязкой к категориям)
- CRUD операции для **единиц измерения** (граммы, литры, штуки и т.д.)
- CRUD операции для **категорий рецептов**
- Inline-редактирование записей

---

## 🚀 Быстрый Старт

### ⚡ Рекомендуемый Способ

**Просто откройте приложение в браузере — всё уже настроено и работает:**

👉 **[https://coolinaa-smart-recipes.vercel.app](https://coolinaa-smart-recipes.vercel.app)**

---

### 💻 Локальный Запуск

Если вы хотите запустить проект локально для разработки или тестирования:

#### Требования
- **Node.js** 20+
- **Java** 21+
- **PostgreSQL** 17+
- **Maven** 3.9+
- **Git**

---

#### 1️⃣ Запуск Backend (Spring Boot)

**Шаг 1: Клонировать репозиторий**
```bash
git clone https://github.com/yourusername/coolinaa-smart-recipes.git
cd coolinaa-smart-recipes/server
```

**Шаг 2: Настроить базу данных**

Создайте базу PostgreSQL:
```sql
CREATE DATABASE coolinaa;
CREATE USER coolinaa_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE coolinaa TO coolinaa_user;
```

**Шаг 3: Настроить переменные окружения**

Создайте файл `.env` или задайте переменные:
```bash
export DB_URL="postgresql://localhost:5432/coolinaa"
export DB_USERNAME="coolinaa_user"
export DB_PASSWORD="your_password"
export JWT_SECRET="your-super-secret-jwt-key-min-256-bits"
export JWT_ACCESS_EXPIRATION="86400000"   # 24 часа
export JWT_REFRESH_EXPIRATION="604800000" # 7 дней
```

Или отредактируйте `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/coolinaa
spring.datasource.username=coolinaa_user
spring.datasource.password=your_password
jwt.secret=your-super-secret-jwt-key-min-256-bits
```

**Шаг 4: Собрать и запустить**
```bash
# Сборка
./mvnw clean package -DskipTests

# Запуск
./mvnw spring-boot:run
```

Backend будет доступен по адресу: **http://localhost:8080**

Swagger UI (документация API): **http://localhost:8080/swagger-ui.html**

---

#### 2️⃣ Запуск Frontend (Angular)

**Шаг 1: Перейти в директорию клиента**
```bash
cd ../client
```

**Шаг 2: Установить зависимости**
```bash
npm install
```

**Шаг 3: Настроить API URL**

Если бэкенд запущен локально, отредактируйте `src/app/core/services/api.service.ts`:
```typescript
private readonly API_BASE = 'http://localhost:8080/api/v1';
```

**Шаг 4: Запустить dev-сервер**
```bash
npm start
```

Фронтенд будет доступен по адресу: **http://localhost:4200**

**Production Build:**
```bash
npm run build
```
Собранное приложение будет в `dist/client/browser/`.

---

#### 3️⃣ Запуск с Docker (Backend)

**Собрать образ:**
```bash
cd server
docker build -t coolinaa-backend .
```

**Запустить контейнер:**
```bash
docker run -d \
  -p 8080:8080 \
  -e DB_URL="postgresql://your-db-host:5432/coolinaa" \
  -e DB_USERNAME="coolinaa_user" \
  -e DB_PASSWORD="your_password" \
  -e JWT_SECRET="your-super-secret-jwt-key-min-256-bits" \
  --name coolinaa-backend \
  coolinaa-backend
```

---

## 📂 Структура Проекта

```
coolinaa-smart-recipes/
├── client/                           # Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── core/                # Сервисы, guards, interceptors
│   │   │   ├── features/            # Страницы приложения
│   │   │   │   ├── auth/            # Вход и регистрация
│   │   │   │   ├── feed/            # Лента рецептов
│   │   │   │   ├── fridge/          # Мой холодильник
│   │   │   │   ├── match/           # Подбор рецептов
│   │   │   │   ├── profile/         # Профиль пользователя
│   │   │   │   ├── recipes/         # Создание и просмотр рецептов
│   │   │   │   └── admin/           # Админ-панель
│   │   │   ├── layout/              # Макеты (main, auth)
│   │   │   ├── shared/              # Переиспользуемые компоненты
│   │   │   └── app.routes.ts        # Маршрутизация
│   │   └── styles.css               # Tailwind CSS
│   ├── angular.json
│   ├── package.json
│   └── vercel.json                  # Конфигурация Vercel
│
├── server/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/coolinaa/
│   │   │   │   ├── config/          # Конфигурация (Security, CORS)
│   │   │   │   ├── controller/      # REST контроллеры
│   │   │   │   ├── dto/             # DTO (request/response)
│   │   │   │   ├── entity/          # JPA сущности
│   │   │   │   ├── repository/      # Spring Data JPA репозитории
│   │   │   │   ├── service/         # Бизнес-логика
│   │   │   │   ├── security/        # JWT, UserDetails
│   │   │   │   ├── exception/       # Обработка ошибок
│   │   │   │   └── mapper/          # Мапперы Entity <-> DTO
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── db/migration/    # Flyway миграции
│   │   └── test/                    # Unit и integration тесты
│   ├── pom.xml
│   ├── Dockerfile                   # Multi-stage Docker build
│   └── .dockerignore
│
├── docs/                            # Документация
│   └── database-queries.md          # SQL запросы для отчёта
│
├── plan-coolinaa.prompt.md          # Техническое задание
├── plan-coolinaaBackend.prompt.md   # Документация бэкенда
└── README.md                        # Этот файл
```

---

## 📖 Документация

### API Эндпоинты

#### 🔐 Аутентификация (`/api/v1/auth`)
```http
POST   /auth/register              # Регистрация
POST   /auth/login                 # Вход
POST   /auth/refresh               # Обновление токена
GET    /auth/me                    # Текущий пользователь
```

#### 🍳 Рецепты (`/api/v1/recipes`)
```http
GET    /recipes                    # Список рецептов (публичные)
GET    /recipes/{id}               # Рецепт по ID
POST   /recipes                    # Создать рецепт
PUT    /recipes/{id}               # Обновить рецепт
DELETE /recipes/{id}               # Удалить рецепт
GET    /recipes/my                 # Мои рецепты
GET    /recipes/match              # Подбор по ингредиентам
```

#### 🥕 Ингредиенты (`/api/v1/ingredients`)
```http
GET    /ingredients                # Список ингредиентов
GET    /ingredients/{id}           # Ингредиент по ID
POST   /ingredients                # Создать (admin)
PUT    /ingredients/{id}           # Обновить (admin)
DELETE /ingredients/{id}           # Удалить (admin)
GET    /ingredients/search         # Поиск по названию
GET    /ingredients/units          # Единицы измерения
```

#### 🧊 Холодильник (`/api/v1/user-ingredients`)
```http
GET    /user-ingredients           # Мои продукты
POST   /user-ingredients           # Добавить продукт
DELETE /user-ingredients/{id}      # Удалить продукт
```

#### ⭐ Отзывы (`/api/v1/reviews`)
```http
GET    /reviews/recipe/{recipeId}  # Отзывы на рецепт
POST   /reviews/recipe/{recipeId}  # Создать отзыв
DELETE /reviews/{id}               # Удалить отзыв
```

#### 📁 Категории
```http
GET    /api/v1/recipe-categories   # Категории рецептов
GET    /api/v1/ingredient-categories # Категории ингредиентов
GET    /api/v1/units               # Единицы измерения
```

**Полная документация:** [Swagger UI](https://coolinaa-smart-recipes.onrender.com/swagger-ui.html)

---

### База Данных

#### Схема (Third Normal Form — 3NF)

**Основные таблицы:**
- `users` — пользователи (username, email, password_hash, role)
- `recipes` — рецепты (title, description, instructions, cooking_time, difficulty_level)
- `ingredients` — справочник ингредиентов
- `recipe_ingredients` — связь рецептов и ингредиентов (M:N)
- `user_ingredients` — холодильник пользователя
- `reviews` — отзывы и оценки
- `recipe_categories` — категории рецептов
- `ingredient_categories` — категории ингредиентов
- `units` — единицы измерения

**Миграции:** `/server/src/main/resources/db/migration/V1__init_schema.sql`

---

### Безопасность

- **JWT токены:** access (24 часа) + refresh (7 дней)
- **BCrypt:** хеширование паролей (cost factor 10)
- **CORS:** настроен для фронтенда (разрешены методы, заголовки, origins)
- **HTTPS:** обязателен для production (Render + Vercel)
- **Валидация:** на уровне DTO (@Valid, @NotNull, @Email)
- **SQL Injection:** защита через JPA параметризованные запросы
- **XSS:** Angular автоматически санитизирует данные

---

## 🎨 Дизайн и UX

### Цветовая Палитра
- **Accent (Акцент):** Янтарный/Оранжевый (#f59e0b, #d97706)
- **Основной:** Камень/Серый (#78716c, #292524)
- **Холодильник:** Небесно-голубой градиент (#38bdf8, #0ea5e9)
- **Успех:** Зелёный (#10b981)
- **Ошибка:** Красный (#ef4444)

### Компоненты
- **Карточки рецептов:** Rounded-2xl, shadow-sm, градиенты
- **Холодильник:** Frost-эффект, backdrop-blur, 3D тени
- **Формы:** Inline validation, autocomplete для ингредиентов
- **Навигация:** Sticky header с иконками и индикаторами

---

## 📊 Производительность

### Backend
- **Lazy Loading** для коллекций JPA (избегаем N+1 запросов)
- **@Transactional** для оптимизации транзакций
- **Индексы** на foreign keys и фильтруемые поля
- **Пагинация** для списков (Page<T> от Spring Data)
- **Кэширование** (потенциал для Redis на топ рецептах)

### Frontend
- **Lazy Loading** модулей через `loadComponent()`
- **RxJS операторы** (`debounceTime`, `distinctUntilChanged`) для поиска
- **Standalone components** (меньше бандл)
- **Tailwind CSS** с PurgeCSS (минимизация CSS)
- **Production build:** AOT, minification, tree-shaking

---

## 📝 TODO / Roadmap

- [ ] Загрузка изображений рецептов (S3/Cloudinary)
- [ ] Email-уведомления (истечение продуктов, новые отзывы)
- [ ] Экспорт рецептов в PDF
- [ ] Социальные функции (подписки, лайки, избранное)
- [ ] Мобильное приложение (Flutter)
- [ ] Рекомендательная система (ML на основе истории)
- [ ] Интеграция с супермаркетами (импорт чеков)
- [ ] Планирование меню на неделю
- [ ] Уведомления об окончании срока годности продкутов в холодиьнике

---

## 👨‍💻 Автор

**Eldar Sayranov**

---

## 📄 Лицензия

MIT License

---

<div align="center">

**Сделано с ❤️ и 🍳**

⭐ Если проект понравился — поставьте звезду на GitHub!

[⬆ Наверх](#-coolinaa--умные-рецепты)

</div>