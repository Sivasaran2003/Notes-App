# 📝 Notes App Backend

A simple backend application for managing personalized notes. Built using Spring Boot with session-based authentication via Spring Security, and MySQL for persistence. Redis is used for storing session data.

---

## ⚙️ System Requirements

- **Java Development Kit (JDK):** 17 or higher  
- **MySQL Database:** 9.3.0 or compatible  
- **Redis Server:** Required for Spring Session  
- **Maven:** 3.9.9 or higher  

---

## 🚀 How to Execute the Code

### 1. Clone the Repository

```bash
git clone https://github.com/Sivasaran2003/Notes-App.git
cd notesApp
```

### 2. Database Setup

- Ensure MySQL is running.
- Create a database named `notes_app_db` or another name of your choice.
- Update `src/main/resources/application.properties` with your DB credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/notes_app_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Redis Setup

Make sure Redis is running (default on `localhost:6379`). The following should already be in `application.properties`:

```properties
spring.session.store-type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### 4. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

App will be available at: [http://localhost:8080](http://localhost:8080)

---

## 📌 API Endpoints

All endpoints are relative to: `http://localhost:8080`

### 🔐 Authentication & User Management

#### 1. Register a New User
- **URL:** `/user`
- **Method:** `POST`
- **Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securepassword123",
  "username": "JohnDoe"
}
```
- **Success (201):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "username": "JohnDoe"
}
```
- **Error (400):** `"User already exists: user@example.com"`

---

#### 2. User Login
- **URL:** `/user/login`
- **Method:** `POST`
- **Request Body:**
```json
{
  "email": "user@example.com",
  "password": "securepassword123"
}
```
- **Success (200):**  
  `"Login successful! Session ID: <your-session-id>"`
- **Error (401):** `"Login failed: Bad credentials"`

---

#### 3. User Logout
- **URL:** `/user/logout`
- **Method:** `POST`
- **Headers:** Requires `JSESSIONID` cookie or `x-auth-token`
- **Success (200):** `"Logout successful!"`
- **Error (400):** `"No active session to logout."`

---

#### 4. Get All Users _(Protected)_
- **URL:** `/user`
- **Method:** `GET`
- **Headers:** Requires authentication
- **Success (302):**
```json
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
```
- **Error (401):** Unauthorized

---

#### 5. Update User _(Protected)_
- **URL:** `/user`
- **Method:** `PUT`
- **Headers:** Requires authentication
- **Request Body:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "username": "Updated Name",
  "password": "newpassword"
}
```
- **Success (200):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "username": "Updated Name"
}
```
- **Error (400):** `"User does not exist"`

---

#### 6. Delete User _(Protected)_
- **URL:** `/user/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` - user ID
- **Headers:** Requires authentication
- **Success (200):** `"User deleted successfully"`
- **Error (400):** `"User does not exist"`

---

## 🗒️ Notes Management (Protected)

All note APIs require valid session authentication.

---

### 1. Get All Notes
- **URL:** `/notes`
- **Method:** `GET`
- **Success (200):**
```json
[
  {
    "noteId": 1,
    "title": "My First Note",
    "content": "This is the content of my first note.",
    "userId": 1
  },
  {
    "noteId": 2,
    "title": "Shopping List",
    "content": "Milk, Eggs, Bread",
    "userId": 1
  }
]
```
- **Error (401):** Unauthorized

---

### 2. Add a New Note
- **URL:** `/notes`
- **Method:** `POST`
- **Request Body:**
```json
{
  "title": "New Idea",
  "content": "Brainstorming session notes.",
  "userId": 1
}
```
- **Success (200):**
```json
{
  "noteId": 3,
  "title": "New Idea",
  "content": "Brainstorming session notes.",
  "userId": 1
}
```
- **Error (401):** Unauthorized

---

### 3. Delete a Note
- **URL:** `/notes/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` - note ID
- **Success (200):** `"note deleted"`
- **Error (400):** `"Note with id <id> not found"`
- **Error (401):** Unauthorized

---

### 4. Update a Note
- **URL:** `/notes/{id}`
- **Method:** `PUT`
- **Path Variable:** `id` - note ID
- **Request Body:**
```json
{
  "title": "Updated Title",
  "content": "This note content has been revised.",
  "userId": 1
}
```
- **Success (200):**
```json
{
  "noteId": 1,
  "title": "Updated Title",
  "content": "This note content has been revised.",
  "userId": 1
}
```
- **Error (400):** If note doesn't exist or user unauthorized  
- **Error (401):** Unauthorized

---

## 👨‍💻 Author

**Sivasaran R.**  
GitHub: [Sivasaran2003](https://github.com/Sivasaran2003)
