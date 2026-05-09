# 🦐 Royyala Diary

> **AI-Powered Shrimp Farm Harvest Cycle Tracker for Farmers in Andhra Pradesh, India**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Gemini AI](https://img.shields.io/badge/Gemini%20AI-1.5%20Flash-purple)](https://ai.google.dev/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

---

## 📖 About The Project

**Royyala** means *prawns/shrimp* in Telugu. Royyala Diary is a full-stack web application built for shrimp farmers in Andhra Pradesh to digitise their farm operations.

### 🔍 Problem
Shrimp farmers in Andhra Pradesh track harvest cycles, feed costs, and profits using **paper notebooks**. This leads to:
- Calculation errors in profit/loss
- No historical data to compare cycles
- No way to predict when shrimp are ready to harvest

### ✅ Solution
Royyala Diary digitises the entire process:
- Track multiple ponds and harvest cycles
- Log weekly feed entries — costs calculated automatically
- Log final harvest — profit/loss calculated automatically
- **Google Gemini AI** predicts the harvest date based on cycle data

### 🌐 Live Demo
> 🔗 **[https://royyala-dairy-production.up.railway.app/](https://royyala-dairy-production.up.railway.app/)**
>
> ⚠️ Free tier — first load may take 30-60 seconds to wake up

---

## 🚀 Features

| Feature | Description |
|---|---|
| 🔐 Farmer Registration & Login | Secure auth with BCrypt password hashing |
| 🏊 Pond Management | Add and manage multiple ponds |
| 🔄 Harvest Cycle Tracking | Start cycles with seed count, cost, and date |
| 🍤 Weekly Feed Logging | Log weekly feed quantity and cost |
| 💰 Auto Profit/Loss Calculation | Revenue, total cost, profit/loss calculated automatically |
| 🤖 AI Harvest Prediction | Google Gemini AI predicts harvest date and weight |
| 📊 Farmer Dashboard | Real-time summary — ponds, active cycles, total profit |
| 📱 Mobile Responsive | Bootstrap 5 — works on any phone or tablet |

---

## 🛠️ Tech Stack

### Backend
- **Java 21** — Latest LTS version
- **Spring Boot 4.0.5** — Auto-configuration, embedded Tomcat
- **Spring Data JPA** — Entity mapping, repository pattern
- **Spring Security** — BCrypt authentication, session management
- **Hibernate 7** — ORM, auto DDL table creation
- **WebClient (WebFlux)** — HTTP client for Gemini AI API calls

### Frontend
- **Thymeleaf** — Server-side template engine
- **Bootstrap 5** — Responsive mobile-first UI
- **CSS3** — Animations, gradients, transitions

### Database
- **MySQL 8** — Relational database
- **Railway** — Cloud MySQL (production)

### AI Integration
- **Google Gemini 1.5 Flash** — Harvest date prediction

### Deployment
- **Render** — Docker-based cloud hosting
- **Docker** — Containerization with Java 21 Alpine image
- **GitHub** — Version control and CI/CD trigger

---

## 🗄️ Database Schema

```
farmers
├── id (PK)
├── full_name
├── email (UNIQUE)
├── password (BCrypt)
├── phone
└── village

ponds
├── id (PK)
├── pond_name
├── area_in_acres
├── location
└── farmer_id (FK → farmers)

harvest_cycles
├── id (PK)
├── start_date
├── seed_count
├── seed_cost
├── status (ACTIVE / COMPLETED)
└── pond_id (FK → ponds)

feed_entries
├── id (PK)
├── week_number
├── feed_date
├── feed_quantity_kg
├── feed_cost
└── cycle_id (FK → harvest_cycles)

harvests
├── id (PK)
├── harvest_date
├── total_kg
├── price_per_kg
├── total_revenue
├── total_feed_cost
├── profit_or_loss
└── cycle_id (FK → harvest_cycles)
```

---

## 💡 Profit/Loss Formula

```
Total Revenue   = Total kg harvested × Price per kg
Total Feed Cost = Sum of all weekly feed costs
Total Cost      = Seed Cost + Total Feed Cost
Profit or Loss  = Total Revenue - Total Cost
```

---

## 🤖 AI Integration

Google Gemini 1.5 Flash is called via Spring WebClient with a domain-specific prompt:

```java
String prompt = "You are an expert shrimp farming advisor for Andhra Pradesh, India.\n"
    + "Cycle details: Seed count: " + cycle.getSeedCount()
    + ", Days running: " + daysRunning
    + ", Total feed: " + totalFeedKg + " kg\n"
    + "Predict: 1. Harvest date  2. Estimated weight  3. Farming advice";
```

**Response parsed from:**
```
candidates[0] → content → parts[0] → text
```

---

## 📂 Project Structure

```
src/main/java/com/royyala/royyaladiary/
├── config/
│   ├── PasswordConfig.java       # BCryptPasswordEncoder bean
│   └── SecurityConfig.java       # Spring Security configuration
├── controller/
│   ├── AuthController.java       # Login, register, dashboard
│   ├── PondController.java       # Pond CRUD
│   ├── HarvestCycleController.java
│   ├── FeedEntryController.java
│   ├── HarvestController.java
│   └── AiController.java         # Gemini AI prediction
├── entity/
│   ├── Farmer.java
│   ├── Pond.java
│   ├── HarvestCycle.java
│   ├── FeedEntry.java
│   └── Harvest.java
├── repository/
│   ├── FarmerRepository.java
│   ├── PondRepository.java
│   ├── HarvestCycleRepository.java
│   ├── FeedEntryRepository.java
│   └── HarvestRepository.java
└── service/
    ├── FarmerService.java         # UserDetailsService implementation
    ├── PondService.java
    ├── HarvestCycleService.java
    ├── FeedEntryService.java
    ├── HarvestService.java
    └── GeminiAiService.java       # Gemini API integration

src/main/resources/
├── templates/
│   ├── fragments/navbar.html     # Shared navbar fragment
│   ├── login.html
│   ├── register.html
│   ├── dashboard.html
│   ├── ponds.html
│   ├── add-pond.html
│   ├── cycles.html
│   ├── start-cycle.html
│   ├── cycle-detail.html
│   ├── add-feed.html
│   ├── log-harvest.html
│   └── error/
│       ├── 404.html
│       └── 500.html
└── application.properties
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8.0+
- Google Gemini API Key (free at [aistudio.google.com](https://aistudio.google.com))

### Local Setup

**1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/royyala-diary.git
cd royyala-diary
```

**2. Create MySQL database**
```sql
CREATE DATABASE royyala_diary;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/royyala_diary
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
gemini.api.key=your_gemini_api_key
```

**4. Build and run**
```bash
mvn clean package -DskipTests
java -jar target/royyaladiary-0.0.1-SNAPSHOT.jar
```

**5. Open in browser**
```
http://localhost:8080
```

---

## 🐳 Docker

```bash
# Build image
docker build -t royyala-diary .

# Run container
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:mysql://host:3306/royyala_diary \
  -e DATABASE_USERNAME=root \
  -e DATABASE_PASSWORD=password \
  -e GEMINI_API_KEY=your_key \
  royyala-diary
```

---

## 🌐 Deployment

The app is deployed on **Render** with **Railway MySQL**:

| Service | Provider |
|---|---|
| Web App | Render (Docker) |
| MySQL Database | Railway |
| AI Predictions | Google Gemini API |

Environment variables configured on Render:
```
DATABASE_URL         = jdbc:mysql://railway-host:3306/railway?useSSL=true
DATABASE_USERNAME    = railway_user
DATABASE_PASSWORD    = railway_password
GEMINI_API_KEY       = your_gemini_key
PORT                 = 8080
```

---

## 📸 Screenshots

### Dashboard
> Clean summary showing ponds, active cycles, total harvests, and overall profit/loss

### Cycle Detail Page
> Weekly feed entries table + AI prediction card + harvest result with profit/loss in color

### Add Harvest Form
> Simple 3-field form — app calculates everything automatically

---

## 🔑 Key Technical Decisions

| Decision | Reason |
|---|---|
| Constructor injection over @Autowired | Prevents circular dependencies, Spring Boot 4 best practice |
| Separate PasswordConfig class | Avoids circular reference between SecurityConfig and FarmerService |
| Java Streams for calculations | Clean, functional-style aggregation without nested loops |
| Gemini 1.5 Flash over 2.0 Flash | More generous free tier quota |
| Railway MySQL over H2 | Persistent production data, same dialect as development |
| Thymeleaf fragments | DRY principle — write navbar once, use everywhere |

---

## 🐛 Challenges Faced

### 1. Circular Dependency in Spring Security
`SecurityConfig → FarmerService → PasswordEncoder → SecurityConfig`

**Fix:** Moved `BCryptPasswordEncoder` bean to a separate `PasswordConfig` class.

### 2. Hibernate MySQL8Dialect Removed
Spring Boot 4 uses Hibernate 7 which removed `MySQL8Dialect`.

**Fix:** Deleted the dialect property — Hibernate 7 auto-detects from connection.

### 3. Gemini API 429 Rate Limit
Free tier limit hit during testing.

**Fix:** Switched to `gemini-1.5-flash`, added try-catch for graceful failure.

### 4. Ambiguous Mapping Error
Two methods mapped to same `POST /ponds/add` URL.

**Fix:** Deleted old method, kept only updated version with `RedirectAttributes`.

---

## 📝 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Tadi Aharon**
- 📧 aharontadi@gmail.com
- 🔗 [linkedin.com/in/tadiaharon](https://linkedin.com/in/tadiaharon)
- 🐙 [github.com/tadiaharon](https://github.com/tadiaharon)

---

> Built with ❤️ for shrimp farmers of Andhra Pradesh
