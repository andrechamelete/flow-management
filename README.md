# Service and process management

Web system for monitoring services and processes at all levels, which includes visualization through a kanban board, flow metrics and flows by node (where it is possible to see all of the organization's services).

## Technologies used:
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

flowchart TD
    A[Client Request: POST /api/auth/login] --> B[AuthController.login()]
    B --> C[AuthRequest (DTO)\n.getEmail()\n.getPassword()]
    B --> D[AuthenticationManager.authenticate()]
    D --> E[UsernamePasswordAuthenticationToken]
    D --> F[UserDetailsImpl\n(authentication.getPrincipal())]
    F --> G[User (entidade do banco)]
    B --> H[JwtUtil.generateToken(UserDetailsImpl)]
    H --> I[createToken()\n(subject: user.getEmail())]
    I --> J[getSigningKey()\n(secretKey decodificado)]
    I --> K[Jwts.builder().compact()]
    B --> L[AuthResponse(token)]

    style A fill:#d9f2ff,stroke:#007acc,stroke-width:2px
    style B fill:#c1e1c1,stroke:#228b22,stroke-width:2px
    style C fill:#fff2cc,stroke:#c47f00
    style D fill:#f9d5e5,stroke:#d63384
    style E fill:#fbeec1,stroke:#e1ad01
    style F fill:#dcd6f7,stroke:#6a5acd
    style G fill:#e2f0cb,stroke:#7c9a55
    style H fill:#ffe0ac,stroke:#d35400
    style I fill:#f7cac9,stroke:#c94c4c
    style J fill:#e0f7fa,stroke:#00acc1
    style K fill:#e6ccff,stroke:#8e44ad
    style L fill:#d1f2eb,stroke:#117864


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
