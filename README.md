**Notes App Backend**
- A simple backend application for managing notes
- Allows users to create, modify, and delete their personalized notes.
- This application uses Spring Boot, Spring Security for session-based authentication, and a MySQL database for data persistence.

**System Requirements**
To run this application, you will need:

Java Development Kit (JDK): Version 17 or higher

MySQL Database: Version 9.3.0 or compatible

Redis Server: For Spring Session to store session data

Maven: Version 3.9.9 or higher

**How to Execute the Code**
Clone the Repository:

git clone https://github.com/Sivasaran2003/Notes-App.git
cd notesApp

**Database Setup:**

Ensure your MySQL server is running.

Create a database for the application (e.g., notes_app_db).

Update your src/main/resources/application.properties with your MySQL database connection details (username, password, database name).

spring.datasource.url=jdbc:mysql://localhost:3306/notes_app_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
spring.jpa.hibernate.ddl-auto=update # or create, create-drop for schema management
spring.jpa.show-sql=true

**Redis Setup:**

Ensure your Redis server is running, typically on localhost:6379.

The application.properties should already have the Redis configuration:

spring.session.store-type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

**Build and Run the Project:**

Navigate to the project root directory in your terminal.

Clean, compile, and build the Maven project:

mvn clean install

Run the Spring Boot application:

mvn spring-boot:run

The application will start on http://localhost:8080 by default.

**API Endpoints**
All endpoints are relative to the base URL: http://localhost:8080.

Authentication & User Management
These endpoints handle user registration, login, logout, and basic user data management.

1. Register a New User
URL: /user

Method: POST

Description: Creates a new user account.

Request Body (JSON):

{
    "email": "user@example.com",
    "password": "securepassword123",
    "username": "JohnDoe"
}

Success Response (201 Created):

{
    "id": 1,
    "email": "user@example.com",
    "username": "JohnDoe"
}

Error Response (400 Bad Request): If user already exists.

"User already exists: user@example.com"

2. User Login
URL: /user/login

Method: POST

Description: Authenticates a user and establishes a session.

Request Body (JSON):

{
    "email": "user@example.com",
    "password": "securepassword123"
}

Success Response (200 OK):

"Login successful! Session ID: <your-session-id>"

Important: The server will send a Set-Cookie header with the JSESSIONID (or x-auth-token header if configured). You must include this cookie/header in subsequent authenticated requests.

Error Response (401 Unauthorized): If credentials are invalid.

"Login failed: Bad credentials"

3. User Logout
URL: /user/logout

Method: POST

Description: Invalidates the current user session.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from the active session.

Success Response (200 OK):

"Logout successful!"

Error Response (400 Bad Request): If no active session.

"No active session to logout."

4. Get All Users (Protected)
URL: /user

Method: GET

Description: Retrieves a list of all registered users. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Success Response (302 Found):

[
    {
        "id": 1,
        "email": "user1@example.com",
        "username": "User One"
    },
    {
        "id": 2,
        "email": "user2@example.com",
        "username": "User Two"
    }
]

Error Response (401 Unauthorized): If not authenticated.

5. Update User (Protected)
URL: /user

Method: PUT

Description: Updates an existing user's details. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Request Body (JSON):

{
    "id": 1,
    "email": "user@example.com",
    "username": "Updated Name",
    "password": "newpassword" // Password will be re-hashed
}

Success Response (200 OK):

{
    "id": 1,
    "email": "user@example.com",
    "username": "Updated Name"
    // Other updated user details
}

Error Response (400 Bad Request): If user does not exist.

"User does not exist"

6. Delete User (Protected)
URL: /user/{id}

Method: DELETE

Description: Deletes a user by their ID. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Path Variable: {id} - The ID of the user to delete.

Success Response (200 OK):

"User deleted successfully"

Error Response (400 Bad Request): If user does not exist.

"User does not exist"

**Notes Management**
These endpoints handle CRUD operations for notes. All notes endpoints require authentication.

1. Get All Notes
URL: /notes

Method: GET

Description: Retrieves a list of all notes. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Success Response (200 OK):

[
    {
        "noteId": 1,
        "title": "My First Note",
        "content": "This is the content of my first note.",
        "userId": 1 // Assuming notes are linked to users
    },
    {
        "noteId": 2,
        "title": "Shopping List",
        "content": "Milk, Eggs, Bread",
        "userId": 1
    }
]

Error Response (401 Unauthorized): If not authenticated.

2. Add a New Note
URL: /notes

Method: POST

Description: Creates a new note. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Request Body (JSON):

{
    "title": "New Idea",
    "content": "Brainstorming session notes.",
    "userId": 1 // Link to the authenticated user's ID
}

Success Response (200 OK):

{
    "noteId": 3,
    "title": "New Idea",
    "content": "Brainstorming session notes.",
    "userId": 1
}

Error Response (401 Unauthorized): If not authenticated.

3. Delete a Note
URL: /notes/{id}

Method: DELETE

Description: Deletes a note by its ID. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Path Variable: {id} - The ID of the note to delete.

Success Response (200 OK):

"note deleted"

Error Response (400 Bad Request): If note does not exist or user is not authorized.

"Note with id <id> not found"

Error Response (401 Unauthorized): If not authenticated.

4. Update a Note
URL: /notes/{id}

Method: PUT

Description: Updates an existing note by its ID. Requires authentication.

Headers: Requires the JSESSIONID cookie (or x-auth-token header) from an active session.

Path Variable: {id} - The ID of the note to update.

Request Body (JSON):

{
    "title": "Updated Title",
    "content": "This note content has been revised.",
    "userId": 1 
}

Success Response (200 OK):

{
    "noteId": 1,
    "title": "Updated Title",
    "content": "This note content has been revised.",
    "userId": 1
}

Error Response (400 Bad Request): If note does not exist or user is not authorized.

Error Response (401 Unauthorized): If not authenticated.
