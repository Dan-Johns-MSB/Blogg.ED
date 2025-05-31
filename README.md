# Blogg.ED – Spring Boot Блог-платформа

## 📝 Опис

Простий блог-сайт, реалізований за допомогою **Spring Boot**, **Thymeleaf** та **Spring Security**. Користувачі можуть реєструватися, створювати пости, коментувати їх та редагувати профіль.

---

## 🗂 Структура проєкту
src/
├── main/
│ ├── java/com.example.blogged/
│ │ ├── controller/ // Контролери: реєстрація, логін, пости, профіль
│ │ ├── model/ // Моделі: User, Post, Comment, Profile
│ │ ├── repository/ // Spring Data репозиторії
│ │ ├── service/ // Сервіси + безпека
│ │ └── BloggedApplication.java
│ └── resources/
│ ├── static/ // Статичні ресурси (CSS)
│ ├── templates/ // HTML-шаблони (Thymeleaf)
│ └── application.properties

---

## Як запустити

1. **Встановити залежності** (підтягнути залежності Maven через pom.xml).
2. **Налаштувати базу даних MySQL** у `application.properties` (створіть за наданим сценарієм та замініть spring.datasource.url на ваше).
3. **Запустити** через IDE або командою:
   ```bash
   ./mvnw spring-boot:run
4. У браузері зайти на https://localhost:8080
