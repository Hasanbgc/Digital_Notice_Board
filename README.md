# DNB - Digital Notice Board

A modern, hyper-local community platform built with **Compose Multiplatform**, enabling organizations and communities to publish and receive location-aware notices in real time.

Digital Notice Board is designed to replace traditional notice boards with a scalable digital solution where schools, universities, residential communities, mosques, hospitals, government offices, and local organizations can instantly share announcements with the right audience.

## ✨ Features

- 📍 **Location-Based Notice Delivery**
  - Discover notices based on your current location or selected community.

- 🏘️ **Community Management**
  - Join multiple communities and receive relevant announcements.

- 📝 **Rich Notice Creation**
  - Publish notices with titles, descriptions, images, videos, documents, categories, priorities, and expiration dates.

- 🔔 **Real-Time Notifications**
  - Receive instant updates for important and emergency announcements.

- 📎 **Media Attachments**
  - Support for images, videos, and document attachments with optimized previews.

- ❤️ **Save & Bookmark**
  - Save important notices for future reference.

- 🔍 **Advanced Search & Filtering**
  - Filter notices by category, priority, location, community, and keywords.

- 🌙 **Modern Material Design**
  - Built with Jetpack Compose using Material Design 3.

- 📶 **Offline Support**
  - Local caching enables users to browse previously loaded notices without an internet connection.

---

# 🏗️ Architecture

The project follows **Clean Architecture** with a **multi-module** structure to ensure scalability, maintainability, and independent feature development.

```
Presentation
    ↓
Domain
    ↓
Data
    ↓
Remote / Local
```

### Project Modules

- Authentication
- Notice Feed
- Core
---

# 🛠️ Tech Stack

## Mobile

- Kotlin
- Compose Multiplatform
- Jetpack Compose
- Material 3
- Navigation 3

## Architecture

- Clean Architecture
- MVVM
- MVI
- Multi-module Architecture
- Repository Pattern
- Dependency Injection (Koin)

## Networking

- Ktor Client
- Kotlin Serialization
- REST API

## Backend

- Supabase
- PostgreSQL
- Row Level Security (RLS)

## Local Storage

- Room Database
- DataStore

## Async

- Kotlin Coroutines
- Flow
- StateFlow

## Maps & Location

- Google Maps
- Location Services

---

# 🚀 Project Goals

- Replace physical notice boards with a digital platform.
- Deliver hyper-local information efficiently.
- Support emergency and community announcements.
- Provide a scalable architecture suitable for large communities.
- Share a single codebase across multiple platforms using Compose Multiplatform.

---

# 📱 Screens

> Screenshots coming soon.

---

# 📌 Future Roadmap

- Push Notifications
- Community Verification
- Admin Dashboard
- Analytics
- AI-powered Notice Categorization
- Event Management
- Polls & Surveys
- QR-based Community Join
- Desktop & iOS Releases

---

# 🤝 Contributing

Contributions, feature requests, and suggestions are welcome.

Feel free to fork the repository and submit a pull request.

---

# 📄 License

This project is licensed under the MIT License.
