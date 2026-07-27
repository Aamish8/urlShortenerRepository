# 🔗 URL Shortener Service

A production-ready **URL Shortener REST API** built with **Spring Boot**, featuring custom short URL generation, redirect support, analytics, Redis caching, Docker support, and comprehensive unit testing.

## 🚀 Features

* Shorten long URLs into unique short links
* Redirect users to the original URL
* URL analytics (click count, creation time, etc.)
* Redis caching for faster URL lookups
* RESTful API with Swagger/OpenAPI documentation
* Dockerized application for easy deployment
* Unit tested using JUnit 5, Mockito, and MockMvc

## 🛠️ Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* Redis
* Docker & Docker Compose
* Lombok
* Swagger / OpenAPI
* JUnit 5
* Mockito
* MockMvc
* Maven

## 📂 Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── config
└── util
```

## 📌 API Endpoints

### Create Short URL

```
POST /api/v1/urls
```

Request

```json
{
  "originalUrl": "https://google.com"
}
```

Response

```json
{
  "shortCode": "ABC123",
  "shortUrl": "http://localhost:8080/ABC123",
  "originalUrl": "https://google.com"
}
```

---

### Redirect

```
GET /api/v1/urls/{shortCode}
```

Returns **302 FOUND** and redirects to the original URL.

---

### URL Analytics

```
GET /api/v1/urls/{shortCode}/stats
```

Returns analytics information such as click count and URL details.

## 🐳 Running with Docker

Clone the repository

```bash
git clone https://github.com/Aamish8/urlShortenerRepository.git
cd urlShortenerRepository
```

Build and start the containers

```bash
docker compose up --build
```

Stop the application

```bash
docker compose down
```

## ▶️ Running Locally

```bash
mvn clean install
mvn spring-boot:run
```

## 📖 API Documentation

After starting the application, open:

```
http://localhost:8080/swagger-ui/index.html
```

## ✅ Testing

Run all unit tests:

```bash
mvn test
```

Tests include:

* Service layer unit tests using Mockito
* Controller layer tests using MockMvc
* Mocked repository and Redis interactions

## Future Improvements

* Custom aliases for short URLs
* URL expiration
* User authentication
* Rate limiting
* QR code generation
* Dashboard for analytics

## 👨‍💻 Author

**Aamish**

If you found this project useful, feel free to ⭐ the repository.
