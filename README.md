# Decola Avanade 2025
Java RESTful API criada para o Decola Avanade 2025.

## Diagrama de classe

```mermaid
classDiagram
    class User {
        +String name
        +String email
    }

    class Game {
        +String title
        +String platform
        +Date completionDate
        +String notes
    }

    User "1" --> "vários" Game : possui
```
