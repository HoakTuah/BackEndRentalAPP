# Rental API Application Backend

This is a Spring Boot application for managing rentals, users, and messages with image upload capabilities.

Before running the application, make sure you have the following installed:

- Node.js and npm (for Angular frontend)
- Angular CLI version 14 or higher
- Spring Boot 3.x

- Java JDK 17 or higher
- Maven
- JWT for authentication
- Swagger for API documentation

- MySQL 8.x
- Environment variables

- Postman (for API testing)
- Mockoon (for API mocking)

- Cloudinary for image storage



## Setup Steps

### 1. Clone the repository

    - git clone https://github.com/HoakTuah/BackEndRentalAPP.git

## 2. Installing Java Development Kit (JDK)

    - Download and install from : https://www.oracle.com/java/technologies/downloads/#java21
    - Run the installer
    - now Install vscode extension for java (if you use vscode IDE)

## 3. Node.js Installation

    - Download and install from : https://nodejs.org/en/download/
    - Run the installer

## 4. Angular CLI version 14 or higher Installation

    - Open a terminal and run:
    - npm install -g @angular/cli@14

## 5. Verify All Installations

    - Open a terminal and run:
        - java -version
        - node -v
        - npm -v
        - ng version

### 6. MySQL Setup

    - Download and install from : https://dev.mysql.com/downloads/mysql/
    - Open a terminal and run:
        - mysql -u root -p
        - enter password
    - Create Database:
        - CREATE DATABASE rentaluser;
        - Create specific user with all privileges on the database rentaluser:
        - CREATE USER 'rentaluser'@'localhost' IDENTIFIED BY 'rentaluser';
        - GRANT ALL PRIVILEGES ON rentaluser.* TO 'rentaluser'@'localhost';
        - FLUSH PRIVILEGES;

### 7. Cloudinary Setup

    - Create an account at : https://cloudinary.com/
    - From your dashboard, copy:
        - Cloud Name
        - API Key
        - API Secret

### 8. Environment Variables Setup

    - For each private variable, create an environment variable in your system.

### Database Configuration

    - DB_USERNAME=your_username
    - DB_PASSWORD=your_password

### JWT Configuration

    - JWT_SECRET=your_jwt_secret
    - JWT_EXPIRATION=your_jwt_expiration

### Cloudinary Configuration

    - CLOUDINARY_CLOUD_NAME=your_cloud_name
    - CLOUDINARY_API_KEY=your_api_key
    - CLOUDINARY_API_SECRET=your_api_secret

### 9. Run the application

    - Open a terminal and run:
        - mvn clean install
        - mvn spring-boot:run

### 10. test with postman and swagger if needed

    - Open postman
    - Import the collection from the postman folder
    - Run the collection
    - Test the API

### 11. swagger documentation

    - Be sure to run the application
    - Open your browser and go to : http://localhost:3001/swagger-ui/index.html
    - From swagger, you can test the API
    - You can also see the API documentation and the endpoints that will explain the request and response :

### 12. API Endpoints Overview      

### Authentication
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - Login user
- GET `/api/auth/me` - Get current user profile

### Rentals
- GET `/api/rentals` - List all rentals
- GET `/api/rentals/{id}` - Get rental details
- POST `/api/rentals` - Create new rental
- PUT `/api/rentals/{id}` - Update rental

### Messages
- POST `/api/messages` - Send message


### 13. Angular frontend

    - Open a terminal and run:
        - ng serve
    - Open your browser and go to: 
        - http://localhost:4200/
    - You can now use the application





