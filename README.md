## Запуск
\`\`\`bash
mvn spring-boot:run
\`\`\`
http://localhost:8080


## API Endpoints
- POST /api/products - добавить товар
- PUT /api/products/{id} - редактировать товар
- GET /api/products?type=DESKTOP - просмотр по типу
- GET /api/products/{id} - просмотр по ID

## Запуск тестов
\`\`\`bash
mvn test
\`\`\`

## Технологии
- Spring Boot 3.1.5
- JPA/Hibernate
- H2 Database
- Thymeleaf
