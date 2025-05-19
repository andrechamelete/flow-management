# Service and process management

## Web system for monitoring services and processes at all levels, which includes visualization through a kanban board, flow metrics and flows by node (where it is possible to see all of the organization's services).

### Technologies used:
- Back-end: Java, Spring Boot, JWT
- Database: PostgreSQL
- Front-end: Typescript with Angular

```mermaid
classDiagram
    class User {
        +Long id
        +String username
        +String email
        +String password
        +LocalDateTime created_at
        +getId(): Long
        +setId(Long): void
        +getUsername(): String
        +setUsername(String): void
        +getPassword(): String
        +setPassword(String): void
    }

    class UserRepository {
        <<interface>>
        +existsByEmail(String): boolean
        +findByEmail(String): Optional<User>
        +findAll(): List<User>
    }

    class UserController {
        +getAllUsers(): List<User>
    }

    UserRepository ..|> JpaRepository
    UserController --> UserRepository
    UserRepository --> User

```
