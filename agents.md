
# AGENTS.md

## Project

This is a Java 17 Spring Boot 3.3.7 application using:

* Spring MVC
* Spring Data JPA / Hibernate
* PostgreSQL
* Maven
* Lombok

Frontend:

* React
* TypeScript
* Vite
* REST API

Do NOT introduce another frontend framework unless explicitly requested.

## Architecture

Backend:

```text
Controller → Service → Repository → Database
```

Frontend:

```text
Page → Component → Service/API → Backend API
```

Keep controllers thin and put business logic in services.

Use:

* `@RestController` for JSON/API endpoints.
* `@Controller` only when server-side rendered pages are explicitly required.
* Existing URL and package conventions must be preserved.

## Backend Rules

Keep business logic inside the Service layer.

Repositories should handle database access and queries.

Use DTOs for API request/response when the existing project already follows this pattern.

Do not expose JPA entities directly from APIs unless the existing project already does so.

Do not rename existing endpoints, fields, or database columns unnecessarily.

## React & TypeScript

Frontend source is typically located under:

```text
src/
```

Follow the existing frontend structure and conventions.

Prefer:

* Functional React components.
* TypeScript interfaces/types.
* Reusable components.
* Small components with clear responsibilities.
* API/service functions separated from UI components.
* Existing routing and state-management patterns.

Avoid:

* `any` unless genuinely necessary.
* Unnecessary global state.
* Large components containing too much logic.
* Duplicated API calls or UI logic.
* Introducing new libraries without a clear reason.

Example:

```tsx
interface Stock {
    id: number;
    symbol: string;
    name: string;
}

export function StockCard({ stock }: { stock: Stock }) {
    return (
        <div>
            <h3>{stock.symbol}</h3>
            <p>{stock.name}</p>
        </div>
    );
}
```

## API Integration

Keep API calls in dedicated service/API files when possible.

Example:

```ts
export async function getStocks(): Promise<Stock[]> {
    const response = await api.get<Stock[]>("/stocks");
    return response.data;
}
```

Components should focus on UI and user interaction rather than containing large amounts of API logic.

Before creating a new endpoint, check whether an existing API can be reused.

## Database

PostgreSQL is used with JPA/Hibernate.

Before changing entities or database-related code:

* Check existing relationships and queries.
* Do not rename columns or fields unnecessarily.
* Do not delete or modify data unless explicitly requested.

## Configuration & Security

Never hardcode:

* Passwords
* API keys
* Tokens
* Database credentials
* SMTP credentials
* Other secrets

Use the project's existing configuration and environment-variable conventions.

Do not introduce JWT/OAuth/security changes unless explicitly requested.

## Coding Rules

Prefer:

* Simple and readable code.
* Small methods and components.
* Existing project conventions.
* Minimal changes.
* Reusable utilities/services/components.
* Strong TypeScript typing.

Avoid unnecessary refactoring, new dependencies, or architectural changes.

Do not modify unrelated files.

## Debugging

When something does not work, inspect first:

1. Application startup/logs
2. Port configuration
3. Controller/API mapping
4. Service
5. Repository/database
6. React component
7. API/service function
8. Browser console
9. Network request/response
10. CORS configuration

Understand HTTP errors:

```text
Connection refused → application/port problem
404 → endpoint mapping problem
403 → forbidden/security problem
401 → authentication problem
500 → server-side error
CORS error → frontend/backend origin configuration
```

## Git

Before and after changes:

```bash
git status
git diff
```

Do not use destructive Git commands.

Do not create commits unless explicitly requested.

## OpenCode Workflow

For non-trivial tasks:

1. Inspect the existing code first.
2. Identify the backend and frontend files involved.
3. Explain the cause/approach.
4. Make the smallest necessary change.
5. Test the change.
6. Review the final diff.

Do not rewrite the project just to implement a small feature.

Because this is also a learning project, explain important decisions and why the solution works.

## Final Checklist

Before finishing:

* Backend compiles.
* Frontend compiles.
* Relevant tests pass.
* API integration works.
* Existing functionality is preserved.
* No secrets were added.
* No unnecessary dependencies were added.
* No unrelated files were changed.
* `git diff` has been reviewed.
