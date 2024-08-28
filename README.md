# Хранилище файлов (Java, Spring Boot, PostgreSQL)

## Описание решения

Созданное приложение является микросервисом, который выполняет роль хранилища различных файлов
и их атрибутов. Приложение удовлетворяет следующим заявленным требованиям:
- Реализована возможность создания файла;
- Реализована возможность получения файла по идентификатору;
- Реализована возможность получения списка всех файлов с использованием пагинации и 
сортировки по времени создания файлов;
- Проект покрыт тестами на JUnit;
- Приложение и база данных обернуты в Docker-контейнеры.

Описание решения поставленной задачи включает в себя следующие этапы:
1. Инициализирован проект, в его структуре выделены основные компоненты: контроллеры, 
сервисы, сущности, репозитории и мапперы;
2. Создана сущность `FileEntity`, которая представляет файл и его атрибуты (id, название, 
дата создания, описание и содержимое в формате Base64);
3. Разработаны DTO классы (`FileCreateDto` и `FileGetDto`) для передачи данных между клиентом 
и сервером;
4. Реализован репозиторий `FileRepository`, который предоставляет стандартные CRUD операции 
для работы с данными;
5. Настроена конфигурация Spring Data JPA для взаимодействия с базой данных;
6. В сервисе `FileService` реализована основная логика работы с файлами (создание, 
получение файла по id и получение списка всех файлов). Логика включает в себя 
обработку данных, преобразование DTO в сущности и работу с репозиторием;
7. Разработан контроллер `FileController` для обработки запросов, поступающих от пользователя;
8. Проект был покрыт тестами на основе JUnit и Mockito. Тесты включают в себя проверку основных 
сценариев работы контроллера и сервисного слоя приложения;
9. Приложение и база данных обернуты в Docker-контейнеры. Для возможности их совместного запуска 
использовался Docker Compose.

Логику работы приложения можно описать следующим образом: пользователь отправляет запрос со всеми
необходимыми данными. Этот запрос обрабатывается контроллером, в котором вызывается соответствующий
метод сервиса. Для получения результата на сервисе происходит взаимодействие с репозиторием, а также преобразования
DTO в сущности и наоборот. По итогу работы приложения пользователь получает необходимую ему информацию,
а также статус ответа (например, 200 в случае успешного ответа на запрос).

## Инструкция по запуску приложения

Чтобы запустить приложение, выполните следующие шаги:
1. Загрузите исходный код проекта на Ваш компьютер (например, клонируйте репозиторий с 
GitHub или скачайте код в ввиде архива);
2. Перейдите в каталог проекта;
3. Убедитесь, что Docker и Docker Compose установлены на Вашем компьютере;
4. В терминале выполните команду для сборки и запуска Docker-контейнеров:
```
docker-compose up
```
Приложение будет запущено локально на порте 8080.

## Примеры тестовых запросов для проверки API-методов

### 1. Создание файла

**Endpoint:** `http://localhost:8080/api/file-storage/create`

**Метод:** `POST`

**Тело запроса:**
```json
{
  "title": "Sample File",
  "description": "This is a sample file",
  "fileContent": "Base64 encoded content"
}
```

**Пример ответа:**
```json
1
```

### 2. Получение файла по идентификатору

**Endpoint:** `http://localhost:8080/api/file-storage/get`

**Метод:** `GET`

**Параметры запроса:** `id` — уникальный идентификатор файла.

**Пример ответа:**
```json
{
  "title": "Sample File",
  "creationDate": "2024-08-28T21:12:55.501733",
  "description": "This is a sample file",
  "fileContent": "Base64 encoded content"
}
```

### 3. Получение списка всех файлов

**Endpoint:** `http://localhost:8080/api/file-storage/get-all`

**Метод:** `GET`

**Параметры запроса:** 
- `pageNumber` (по умолчанию: 0) — номер страницы;
- `pageSize` (по умолчанию: 10) — размер страниц.

**Пример ответа:**
```json
[
  {
    "title": "Sample File",
    "creationDate": "2024-08-28T21:12:55.501733",
    "description": "This is a sample file",
    "fileContent": "Base64 encoded content"
  }
]
```