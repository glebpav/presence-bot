
<br>
<br>
<div align="center">
  <img src="https://xelari.com/logo_xelari.svg" width="300" alt="Xelari Logo">

  <h1>Presence Bot</h1>

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Proprietary-blue.svg)](#license)

  <p>A sophisticated Telegram bot solution for employee presence tracking and team management at Xelari.</p>
</div>
<br>


## 📌 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Deployment](#-deployment)
- [License](#-license)

## ✨ Features

### Team Management
- 🏗️ Create and organize teams with hierarchical roles
- 🔑 Secure invitation system with expiring tokens
- 👥 Member management with different permission levels

### Meeting Coordination
- 🗓️ Schedule and track meetings
- ✅ Attendance confirmation system
- 📊 Participation analytics

### User System
- 👤 Telegram-based authentication
- 🔄 Synchronization with company directory
- 🔒 Role-based access control

## 🛠 Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL

**Infrastructure:**
- Docker
- Docker Compose
- Gradle

**Telegram Integration:**
- TelegramBots API
- Long Polling support

## 🏛 Architecture

```
src/
├── main/
│   ├── java/
│   │   └── com/xelari/presencebot/
│   │       ├── application/      # Use cases and application services
│   │       ├── domain/           # Core business logic and entities
│   │       └── telegram/         # Telegram integration layer
│   └── resources/                # Configuration files
└── test/                         # Unit and integration tests
```

Follows Clean Architecture principles with clear separation between:
- Domain layer (business logic)
- Application layer (use cases)
- Infrastructure layer (external services)

## 🚀 Getting Started

### Prerequisites
- Java 17 JDK
- Docker 20.10+
- Telegram bot token from [@BotFather](https://t.me/BotFather)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/xelari/presence-bot.git
   cd presence-bot
   ```

2. Set up environment variables:
   ```bash
   cp env/.env.example env/.env
   # Edit the .env file with your configuration
   ```

3. Build and run:
   ```bash
   ./gradlew build
   docker-compose up -d
   ```

## ⚙️ Configuration

Key configuration options in `application.yml`:

```yaml
bot:
  name: "XelariPresenceBot"
  token: ${BOT_TOKEN} # Set in env/.env

invitation:
  token-expiration-days: 1 # Token validity period
```

## 📚 API Documentation

### Telegram Commands
| Command | Description | Access Level |
|---------|-------------|--------------|
| `/start` | Register user | All |
| `/team` | Team management | All |
| `/create_team` | Create new team | Managers |
| `/invite` | Generate invitation | Managers |
other commands are in progress ...

## 🚀 Deployment

### Docker Deployment
```bash
docker-compose up -d --build
```



## 📜 License

This project is proprietary software developed for and owned by Xelari. All rights reserved.

---

<div align="center">
  <sub>Built with ❤︎ by <a href="https://xelari.com">Xelari Team</a></sub>
</div>