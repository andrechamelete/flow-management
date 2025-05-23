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
        - String secretKey
        - Key getSigningKey()
        + String extractUsername(String token)
        + Date extractExpiration(String token)
        + <T> T extractClaim(String token, Function<Claims, T>)
        + String generateToken(UserDetails userDetails)
        + boolean validateToken(String token, UserDetails userDetails)
        - String createToken(Map<String, Object>, String)
        - Claims extractAllClaims(String token)
        - boolean isTokenExpired(String token)
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

    class AuthController {
        - AuthenticationManager authenticationManager
        - JwtUtil jwtUtil
        + AuthResponse login(AuthRequest authRequest)
    }

    class AuthRequest {
        - String email
        - String password
        + getEmail() String
        + setEmail(String)
        + getPassword() String
        + setPassword(String)
    }

    class UserService {
      <<interface>>
      + User findByEmail(String)
      + User create(User)
    }

    class UserServiceImpl {
      - UserRepository userRepository
      + User findByEmail(String)
      + User create(User)
    }

    %% ========= SECURITY ========= %%
    class UserDetailsServiceImpl {
      - UserRepository userRepository
      + UserDetails loadUserByUsername(String)
    }

    class JwtRequestFilter {
      - JwtUtil jwtUtil
      - UserDetailsServiceImpl userDetailsService
      + doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
    }

    class SecurityConfig {
      - JwtRequestFilter jwtRequestFilter
      - UserDetailsServiceImpl userDetailsService
      + SecurityFilterChain securityFilterChain(HttpSecurity)
      + AuthenticationManager authenticationManager(AuthenticationConfiguration)
      + AuthenticationProvider authenticationProvider()
      + PasswordEncoder passwordEncoder()
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

    AuthController --> AuthRequest
    AuthController --> AuthResponse
    AuthController --> JwtUtil
    JwtUtil --> UserDetails
    UserDetailsImpl --> User
    UserDetailsImpl ..|> UserDetails
    JwtUtil --> Claims
    JwtUtil --> Function

    JwtRequestFilter --> JwtUtil
    JwtRequestFilter --> UserDetailsServiceImpl
    SecurityConfig --> JwtRequestFilter
    SecurityConfig --> UserDetailsServiceImpl
    UserDetailsServiceImpl --> User
    JwtRequestFilter --> UserDetails
    JwtUtil --> UserDetails
    UserServiceImpl --> User
    UserRepository --> User

```

## Tables
```mermaid
    organization {
        int id PK
        string name
        date created_at
        int id_user FK
    }

    flow {
        int id
        int id_organization
    }

    organization ||--o{ flow : "has"
```
