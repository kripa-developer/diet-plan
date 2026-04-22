# 🥗 NutriPlan — Premium Diet Planning System
NutriPlan is a full-stack, feature-rich web application designed to help users manage their nutrition, build personalized weekly diet plans, and track their health goals using the Harris-Benedict formula.
## ✨ Features
- **🔐 Secure Authentication**: JWT-based login and registration with role-based access (User/Admin).
- **📊 Health Dashboard**: Real-time BMI calculation and daily calorie target tracking.
- **🥗 Meal Library**: A searchable database of 20+ pre-seeded meals with full macro-nutrient data (Calories, Protein, Carbs, Fat, Fiber).
- **📋 Diet Planner**: Build and manage multiple 7-day diet plans. Add meals to specific days (Monday–Sunday) and time slots.
- **⚙️ Admin Panel**: A dedicated interface for admins to manage the meal library (Add/Edit/Delete meals).
- **🎨 Premium UI**: Modern design with glassmorphism, smooth animations, and a responsive layout built with Angular Material.
## 🛠️ Tech Stack
**Backend:**
- **Java 21** & **Spring Boot 3**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **MySQL** (Persistence)
- **Lombok** & **Jakarta Validation**
**Frontend:**
- **Angular 16**
- **Angular Material** (UI Components)
- **Chart.js** (Macro Visualization)
- **CSS3** (Custom Design System)
**DevOps:**
- **Docker** & **Docker Compose**
- **Nginx** (Reverse Proxy & Frontend Hosting)
## 🚀 Getting Started
### Prerequisites
- **Java 17+**
- **Node.js 16+** & **Angular CLI**
- **MySQL Server** (if running locally without Docker)
- **Docker Desktop** (optional, for containerized setup)
### 1. Local Setup (Development)
#### Backend
1. Create a MySQL database named `dietplandb`.
2. Configure your credentials in `diet-plan-backend/src/main/resources/application.properties`.
3. Run the backend:
   ```bash
   cd diet-plan-backend
   mvn spring-boot:run
   ```
   *The system will auto-seed initial data on first run.*
#### Frontend
1. Install dependencies:
   ```bash
   cd diet-plan-frontend
   npm install --legacy-peer-deps
   ```
2. Run the frontend:
   ```bash
   ng serve
   ```
3. Open `http://localhost:4200` in your browser.
### 2. Docker Setup (Production-ready)
1. Copy `.env.example` to `.env` and update secrets.
2. Run:
   ```bash
   docker-compose up --build -d
   ```
3. Access the application at `http://localhost`.
## 🔑 Default Admin Account
- **Email**: `admin@dietplan.com`
- **Password**: `admin123`
## 📁 Project Structure
```text
diet-plan/
├── diet-plan-backend/    # Spring Boot REST API
├── diet-plan-frontend/   # Angular 16 Frontend
├── docker-compose.yml    # Docker orchestration
└── README.md             # Project documentation
```
## 📜 API Reference
- `POST /api/auth/register` - User Registration
- `POST /api/auth/login` - User Login (returns JWT)
- `GET /api/users/profile` - Get User Stats (BMI/Calories)
- `GET /api/meals` - Browse Meal Library
- `POST /api/diet-plans` - Create a Diet Plan
- `POST /api/diet-plans/{id}/meals` - Add Meal to Plan
---
Built with ❤️ by NutriPlan Team.
