# Service and process management

## Web system for monitoring services and processes at all levels, which includes visualization through a kanban board, flow metrics and flows by node (where it is possible to see all of the organization's services).

### Technologies used:
- Back-end: Java, Spring Boot, JWT
- Database: PostgreSQL
- Front-end: Typescript with Angular

## Register / Login funcionality
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

    class AuthRegisterController {
        +register(request: RegisterRequest): AuthResponse
    }

    class RegisterRequest {
        -String name
        -String email
        -String password
        +getName(): String
        +setName(String): void
        +getEmail(): String
        +setEmail(String): void
        +getPassword(): String
        +setPassword(String): void
    }

    class AuthResponse {
        -String token
        +getToken(): String
        +setToken(String): void
    }

    class JwtUtil {
        +generateToken(UserDetails): String
        +extractUsername(String): String
        +validateToken(String, UserDetails): boolean
    }

    class UserDetailsImpl {
        -User user
        +getAuthorities(): Collection<GrantedAuthority>
        +getPassword(): String
        +getUsername(): String
        +isAccountNonExpired(): boolean
        +isAccountNonLocked(): boolean
        +isCredentialsNonExpired(): boolean
        +isEnabled(): boolean
        +getUser(): User
    }

    AuthRegisterController --> RegisterRequest
    AuthRegisterController --> AuthResponse
    AuthRegisterController --> JwtUtil
    AuthRegisterController --> UserRepository
    AuthRegisterController --> User
    AuthRegisterController --> UserDetailsImpl
    UserDetailsImpl --> User
    JwtUtil --> UserDetailsImpl
    UserRepository --> User
    UserRepository ..|> JpaRepository
    UserController --> UserRepository
    UserRepository --> User

```
