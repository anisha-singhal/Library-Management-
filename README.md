# Library Management — Spring Boot + JSP

A Spring Boot application demonstrating CRUD-style management of two related entities — **Author** and **Book** — with a one-to-many relationship. The project covers all layers: JPA entities, repositories with custom JPQL, service layer, Spring MVC controllers, JSP views, and unit tests.

## Stack

- Java 17
- Spring Boot 3.2.x (Web, Data JPA, Validation)
- JSP + JSTL (Jakarta) views
- H2 in-memory database
- JUnit 5 + Mockito + Spring Test

## Entity Design

```
Author (1) ─────< (N) Book
```

| Author       | Book                          |
|--------------|-------------------------------|
| id (PK)      | id (PK)                       |
| name         | title                         |
| email (UQ)   | isbn (UQ)                     |
| nationality  | price                         |
|              | published_year                |
|              | author_id (FK → Author.id)    |

`Author.books` is a `@OneToMany(mappedBy = "author")` collection; `Book.author` is a `@ManyToOne`.

## Custom Inner-Join Query (Read Operation)

Defined in [`BookRepository`](src/main/java/com/bits/library/repository/BookRepository.java):

```java
@Query("SELECT new com.bits.library.dto.BookAuthorView(" +
       "b.id, b.title, b.isbn, b.price, b.publishedYear, " +
       "a.id, a.name, a.nationality) " +
       "FROM Book b INNER JOIN b.author a " +
       "ORDER BY a.name ASC, b.title ASC")
List<BookAuthorView> findAllBooksWithAuthor();
```

This is rendered in [`books/list.jsp`](src/main/webapp/WEB-INF/views/books/list.jsp) with each row showing both book and author fields.

## Project Layout

```
src/main/java/com/bits/library/
  LibraryApplication.java
  entity/   (Author, Book)
  dto/      (BookAuthorView)
  repository/
  service/
  controller/   (Home, Author, Book, GlobalExceptionHandler)
  exception/    (ResourceNotFoundException, DuplicateResourceException)
  config/       (DataLoader — seeds 10 authors + 10 books)

src/main/resources/
  application.properties
  static/css/styles.css

src/main/webapp/WEB-INF/views/
  home.jsp, error.jsp
  fragments/      (header.jsp, footer.jsp)
  authors/        (list.jsp, add.jsp, edit.jsp)
  books/          (list.jsp, add.jsp, edit.jsp)

src/test/java/com/bits/library/
  repository/    (AuthorRepositoryTest, BookRepositoryTest — @DataJpaTest)
  service/       (AuthorServiceTest, BookServiceTest — Mockito)
```

## Running

```bash
# from the project root
./mvnw spring-boot:run
# or, on Windows
mvnw.cmd spring-boot:run
```

Then open:

| URL                                   | Purpose                                      |
|---------------------------------------|----------------------------------------------|
| http://localhost:8080/                 | Home                                         |
| http://localhost:8080/authors          | List authors (10 seeded rows)                |
| http://localhost:8080/authors/new      | Add author form                              |
| http://localhost:8080/books            | List books with inner-joined author info     |
| http://localhost:8080/books/new        | Add book form                                |
| http://localhost:8080/h2-console       | H2 console (JDBC URL `jdbc:h2:mem:librarydb`)|

## Operations Covered

1. **Populate Database** — `DataLoader` (`@Bean CommandLineRunner`) seeds 10 authors and 10 books at startup if the table is empty.
2. **Create** — `GET /authors/new`, `GET /books/new` show JSP forms; `POST /authors`, `POST /books` save via the service. Bean Validation errors are surfaced as `field-error` messages; `DuplicateResourceException` (e.g. ISBN already exists) and `DataIntegrityViolationException` are caught and shown as a friendly `error` div.
3. **Read** — `GET /authors` and `GET /books` use the service layer and JSTL/EL to bind data to the JSP views. `GET /books` uses the inner-join custom query.
4. **Update** — `GET /{id}/edit` loads the entity into the form (pre-populated via Spring `form` tag), `POST /{id}` saves. Same exception handling as create.

## Tests

```bash
./mvnw test
```

- `BookRepositoryTest` and `AuthorRepositoryTest` use `@DataJpaTest` and the in-memory H2 to verify the inner-join query and unique-constraint helpers.
- `BookServiceTest` and `AuthorServiceTest` use Mockito to verify business logic in isolation, including duplicate-detection and not-found paths.

## Implementation Notes

- **JSP location**: `src/main/webapp/WEB-INF/views/`. Resolved via `spring.mvc.view.prefix`/`suffix` in `application.properties`. Packaging is `war` so `tomcat-embed-jasper` (provided) compiles the JSPs.
- **JSTL 3 / Jakarta**: tag URIs use the new form, e.g. `jakarta.tags.core` rather than the legacy `http://java.sun.com/...`.
- **Exception handling**: each controller catches the domain exceptions and re-renders the form with an `errorMessage`. A `@ControllerAdvice` (`GlobalExceptionHandler`) is the safety net for `ResourceNotFoundException` and unexpected exceptions.
- **CSS**: a single `styles.css` served from `static/` provides a clean, consistent look across all pages.
