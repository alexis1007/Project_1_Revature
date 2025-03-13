# 2025-03-13 (Thursday)

## Attendees

- Victor Elias (Scrum Master)
- Bertin Vergara (Product Owner)
- Jorge Medina (Developer)
- Alexis Montalvo Lopez (Developer)
- Pablo Bravo (Developer)

## Announcements

The team will refactor the existing Loan Management System from Javalin (with JDBC and a custom DAO layer) to Spring Boot using Spring MVC for RESTful endpoints and Spring Data JPA for data persistence.

## Action items

1. Database Schema Finalization

- Collaboratively design SQL tables adhering to 3rd Normal Form (3NF).

- Ensure entities (Users, Loans) include required fields (e.g., loan status, user roles).

2. Endpoint Assignment

- Distribute RESTful API endpoint development tasks (e.g., /auth/register, /loans) among team members.

- Use Spring MVC annotations (@PostMapping, @GetMapping) for endpoint definitions.

3. Git Workflow

- Adopt feature branches for task-specific development.

- Require pull requests with peer reviews before merging into the main branch.

## Knowledge Sharing

1. **Backlog Refinement Process**
   - **The backlog contains all pending work items (features, bugs, tasks).**
     - Refinement involves:
       - Breaking large user stories into smaller, testable tasks.
       - Estimating effort using story points or hours.
       - Prioritizing items based on sprint goals.
2. **Spring Data JPA Best Practices**
   - Replace JDBC DAOs with Spring Data JPA repositories (e.g., @Repository interfaces).
   - Ensure data integrity via repository methods and transaction management.

## Next Steps

- Schedule a code review session for Tuesday.
