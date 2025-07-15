## Customer Rewards Calculator API

A Spring Boot RESTful API to calculate customer reward points based on monthly transaction history.

## Description

This API computes reward points for each customer using a tiered system:
- No points for transactions ≤ $50
- 1 point for every dollar spent between $50 and $100
- 2 points for every dollar spent above $100

The results are grouped by customer and by month.

---

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- JUnit 5 / Mockito
- Maven

---

## Prerequisites 

- Java 17+
- Maven 3.8+
- IntelliJ IDEA or Eclipse IDE

---

## Getting Started

### 1. Clone the Repository 

git clone https://github.com/Manohar-1608/customer_rewards_api
cd customer_rewards_api

### 2. Run the Application

mvn spring-boot:run

📨 API Endpoint

GET /api/reward-points/count-points?customerId=C1&to=2025-07-01&from=2025-04-01

### Sample Response:

{
    "customerId": "C1",
    "customerName": "Mohan",
    "monthlyPoints": {
        "2025-05": 90,
        "2025-06": 25
    },
    "totalPoints": 115
}




