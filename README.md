## Medical Checkup Project

This repository contains a Spring Boot application developed for the Medical checkup Project. The application demonstrates backend development skills through a medical checkup system including JWT auth, service layers, and modular packages.

---

### Prerequisites

* Java SDK 17 or above
* Apache Maven 3.8.4 or above
* MySQL Database
* Jasper Report
* Digital Ocean Space
* Amazon S3 SDK
* GitHub API

---

### Project Structure

```
src/main/java/com/medical/medical_chekup
├── restcontroller
│   ├── ApiArrivalHistoryController.java
│   ├── ApiBalanceWithdrawController.java
│   ├── ApiDoctorController.java
│   ├── ApiForgetPasswordController.java
│   ├── ApiLocationController.java
│   ├── ApiPasienController.java
│   ├── ApiRegisterController.java
│   └── ApiUserController.java
│
├── security
│   ├── AuthController.java
│   ├── AuthService.java
│   ├── CorsConfiguration.java
│   ├── CustomBasicAuthenticationEntryPoint.java
│   ├── CustomBearerTokenAccessDeniedHandler.java
│   ├── CustomBearerTokenAuthenticationEntryPoint.java
│   ├── JwtProvider.java
│   └── SecurityConfiguration.java
│
├── service
│   ├── impl
│   │   ├── ArrivalHistoryService.java
│   │   ├── BalanceWithdrawService.java
│   │   ├── DoctorService.java
│   │   ├── ForgetPasswordService.java
│   │   ├── LocationService.java
│   │   ├── PasienService.java
│   │   ├── RegisterService.java
│   │   └── UserServicewrong.java
│
├── model
│   ├── MUser.java
│   ├── MRole.java
│   ├── MLocation.java
│   └── ... (Other entity classes)
│
├── dto
│   ├── response
│   │   ├── ApiResponse.java
│   │   ├── ApiResponsePagination.java
│   │   ├── ArrivalHistoryDTO.java
│   │   ├── UserDTO.java
│   │   └── ... (Other DTO classes)
│   └── model
│       └── ... (Model representations)
│
├── dao
│   ├── specs
│   └── ... (Repository interfaces)
│
├── converter
│   └── UserToUserDtoConverter.java
│
├── util
│   └── tesEncodingPass.java
│
└── MedicalChekupApplication.java
```

---

### Design Pattern

#### 1. Layered Architecture (N-tier)

* **Controller Layer** : Handles API requests (e.g., `ApiUserController`)
* **Service Layer** : Business logic (e.g., `UserService`, `RegisterService`)
* **Repository Layer** : Data access with Spring Data JPA (e.g., `UserRepository`)
* **Model Layer** : Entity definition for DB
* **DTO Layer** : Data transfer objects between layers

#### 2. Singleton Pattern

* Configuration classes like `JwtProvider` and `CorsConfiguration`

#### 3. Adapter Pattern

* Converters like `UserToUserDtoConverter`

#### 4. Strategy Pattern (Implicit)

* Can be applied to dynamic service methods or role-based behaviors

#### 5. Exception Handling Pattern

* Global exception handling with Spring `@ControllerAdvice`

---

### API Documentation

Postman collection available: `docs/Maybank Technical Test.postman_collection.json`

#### Examples:

* `/v1/user/login` – Login API
* `/v1/pasien/create` – Register Pasien
* `/v1/location/get-all` – Fetch location data

---

### Built With

* Java 17
* Spring Boot
* Spring Web & Security
* Spring Data JPA
* MySQL
* Jasper Reports
* Digital Ocean Space
* Amazon S3
* Maven
* Lombok

---

### Author

* Nabil Wicaksono

---

### License

Distributed under the MIT License. See `LICENSE` for more information.
