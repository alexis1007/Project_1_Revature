# Loan Management System: Spring Boot & React Application

## **Project Overview**

This project is a **Loan Management System** developed as part of **Iteration 2 (Project 1)**. The goal was to refactor an existing system built with Javalin and JDBC into a modern, full-stack application using **Spring Boot** for the back-end and **React** for the front-end. The application supports user registration, authentication, loan application management, and role-based access control.

---

## **Features**

### **1. User Registration & Authentication**
- **Endpoints:**
  - `POST /auth/register`: Register new users.
  - `POST /auth/login`: Authenticate users and issue a JWT token.
  - `POST /auth/logout`: End the current session.
- **Role Management:**
  - Roles are stored in the database (`ROLE_regular` for regular users and `ROLE_manager` for managers).
  - Role-based access control ensures that only managers can approve/reject loans or manage user types.

### **2. User Profile Management**
- Users can:
  - View and update their own profiles.
  - Delete their accounts (while maintaining referential integrity).

### **3. Loan Applications**
- **Regular Users:**
  - Create new loan applications.
  - View and edit their own loan applications.
- **Managers:**
  - View all loan applications.
  - Approve or reject any loan application.
- **Loan Data Fields:**
  - Amount requested, loan type, and status (*pending*, *approved*, *rejected*).

### **4. Role Management**
- Managers can manage user roles (`UserType`):
  - View all roles.
  - Create, update, or delete roles.

---

## **Technology Stack**

### **Back-End:**
- **Java 17**
- **Spring Boot**
- **Spring MVC** (RESTful APIs)
- **Spring Data JPA** (Data persistence)
- **PostgreSQL** (Database)
- **JWT** (Authentication)
- **Logback** (Logging)

### **Front-End:**
- **React** (with TypeScript)
- **HTML/CSS/JavaScript**

### **Testing:**
- **JUnit 5** (Unit testing)
- **Mockito** (Mocking dependencies)

---

## **Architecture**

### **Back-End:**
- **Controllers:** Handle HTTP requests and responses.
- **Services:** Contain business logic.
- **Repositories:** Use Spring Data JPA for database operations.
- **Entities:** Represent database tables (e.g., `User`, `LoanApplication`, `UserType`).

### **Front-End:**
- **Components:** Modular React components for UI.
- **Services:** Handle API calls to the back-end.
- **Routing:** React Router for navigation.

---

## **Endpoints**

### **Authentication:**
- `POST /auth/register`: Register a new user.
- `POST /auth/login`: Authenticate and receive a JWT token.

### **User Profiles:**
- `GET /user-profiles/{id}`: View a user profile.
- `PUT /user-profiles/{id}`: Update a user profile.
- `DELETE /user-profiles/{id}`: Delete a user profile.

### **Loan Applications:**
- `POST /loans`: Create a new loan application.
- `GET /loans`: View all loans (manager only).
- `GET /loans/user/{id}`: View loans for a specific user.
- `PUT /loans/{id}`: Update a loan application.
- `PUT /loans/{id}/approve`: Approve a loan (manager only).
- `PUT /loans/{id}/reject`: Reject a loan (manager only).

### **User Types:**
- `GET /user_types`: View all user types.
- `POST /user_types`: Create a new user type (manager only).
- `PUT /user_types/{id}`: Update a user type (manager only).
- `DELETE /user_types/{id}`: Delete a user type (manager only).

---

## **Setup Instructions**

### **1. Back-End Setup**
1. Install **Java 17** and **Maven**.
2. Configure the database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/project1
   spring.datasource.username=postgres
   spring.datasource.password=password

3. Run the application:
mvn clean install
mvn spring-boot:run
4. The back-end will be available at http://localhost:7070.

### **2. Front-End Setup**
1. Install Node.js (version 16 or higher).
2. Navigate to the React app directory:
cd react-app/loan-management-app
3. Install dependencies:
npm install
4. Start the development server:
npm start
5. The front-end will be available at http://localhost:3000.
## **Testing**
## ***Unit Tests:**
- Run back-end tests using Maven:
mvn test
## ***Example Test (JUnit + Mockito):**
@Test
public void testValidateUser() {
    User user = new User();
    user.setUsername("testuser");
    user.setPasswordHash(BCrypt.hashpw("password", BCrypt.gensalt(4)));
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

    Optional<User> validatedUser = userService.validateUser("testuser", "password");

    assertTrue(validatedUser.isPresent());
    assertEquals("testuser", validatedUser.get().getUsername());
}

## **Logging**
## ***Logback Configuration:**
Logs are configured in src/main/resources/application.properties:
logging.level.root=INFO
logging.level.org.example=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n


## ***Deliverables**
## ***Source Code:**
- Back-end: Spring Boot application.
- Front-end: React application.
## ***Database Scripts:**
- data.sql for initializing the database.
## ***Documentation:**
- This README file with setup instructions and API details.

## ***Future Enhancements**
- Add integration tests for RESTful endpoints.
- Improve UI/UX with better styling and validation.
- Implement email notifications for loan status updates.