# 🚀 Multi-Tenant SaaS Backend

A production-ready multi-tenant backend system built using Spring Boot, designed to handle tenant-based data isolation with secure authentication and scalable architecture.

---

## 🔧 Tech Stack

- Java 17  
- Spring Boot  
- Spring Security  
- JWT (Authentication)  
- MySQL  
- Docker & Docker Compose  

---

## 💡 Key Features

- Multi-tenant architecture (tenant-level data isolation)  
- JWT-based authentication & authorization  
- Secure signup and login APIs  
- Task management APIs (tenant-aware)  
- Pagination support for scalable data handling  
- Global exception handling  
- Structured logging with context  
- Fully containerized using Docker  

---

## 🏗️ Architecture

The project follows a clean layered architecture:

Controller → Service → Repository → Database

- Stateless authentication using JWT  
- Tenant-aware request handling  
- Separation of concerns for maintainability  

---

## ⚙️ Running the Project (Docker)

### 1. Clone the repository

```bash
git clone https://github.com/gayathriv03/multi-tenant-saas-backend.git
cd multi-tenant-saas-backend
```

---

### 2. Run using Docker Compose

```bash
docker-compose up --build
```

---

### 3. Application will be available at:

```
http://localhost:8082
```

---

## 🔑 API Endpoints

### 🔐 Authentication

Signup  
POST /auth/signup  

Login  
POST /auth/login  

---

### 📋 Tasks (Protected APIs)

GET    /tasks  
POST   /tasks  
PUT    /tasks/{id}  
DELETE /tasks/{id}  

---

## 🧠 Key Learnings

- Implemented multi-tenant backend architecture  
- Handled JWT-based authentication and security  
- Debugged real-world issues in Docker (DB connectivity, container networking)  
- Understood service startup dependencies and production behavior  

---

## 🚀 Deployment

- Containerized using Docker  
- Explored deployment on AWS EC2 (stopped to avoid cost)  

---

## 📦 Project Link

👉 https://github.com/gayathriv03/multi-tenant-saas-backend  

---

## 👩‍💻 Author

Gayathri V  

---

## 📌 Future Improvements

- AWS deployment with public access  
- Redis caching  
- API rate limiting  
- CI/CD pipeline integration  
