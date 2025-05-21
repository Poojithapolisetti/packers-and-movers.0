# Pack_n_Move

## Overview
Pack_n_Move is a web application designed to simplify packers and movers services. Built using Java Servlets, JSP, and Bootstrap, the platform allows users to search for services based on their desired `from` and `to` locations, compare companies by ratings and prices, and book services seamlessly.

---

## Features
1. **User Authentication**
   - Login and Registration functionality.
   - User Profile Management.
2. **Service Search**
   - Search services based on `from` and `to` locations.
3. **Company Comparison**
   - View company details including ratings, prices, and availability.
4. **Service Booking**
   - Book a service after comparing options.
5. **Admin Panel**
   - Manage user transactions.
   - Approve or cancel bookings.
   - Database management capabilities.

---

## Technologies Used
- **Frontend**: HTML,Bootstrap 4.5.2
- **Backend**: Java Servlets and JSP
- **Database**: MySQL

---

## Installation Guide

### Prerequisites
- Java Development Kit (JDK 8+)
- Apache Tomcat Server (9.x or higher)
- MySQL Database
- IDE (e.g., VS Code or Eclipse or IntelliJ IDEA)

## Functionality

1. **User Workflow**
   - Register or log in to the platform.
   - Use the search feature to select your `from` and `to` locations.
   - Browse companies, compare ratings and prices.
   - Choose a company and book the service.

2. **Admin Workflow**
   - Log in to the admin panel.
   - View and manage all transactions.
   - Approve or cancel bookings.
   - Perform database management tasks.

---

## Database Schema
### Table: `userdata`
| Field     | Type         | Description         |
|-----------|--------------|---------------------|
| id        | INT          | Auto-increment ID  |
| username  | VARCHAR(255) | User's name        |
| email     | VARCHAR(255) | User's email       |
| password  | VARCHAR(255) | User's password    |

### Table: `bookings`
| Field         | Type          | Description                |
|---------------|---------------|----------------------------|
| booking_id    | INT           | Auto-increment booking ID |
| user_id       | INT           | User ID (foreign key)     |
| from_location | VARCHAR(255)  | Starting location         |
| to_location   | VARCHAR(255)  | Destination location      |
| company_id    | INT           | Selected company ID       |
| status        | ENUM('Pending','Approved','Cancelled') | Booking status |

### Table: `companies`
| Field         | Type          | Description                |
|---------------|---------------|----------------------------|
| company_id    | INT           | Auto-increment company ID |
| name          | VARCHAR(255)  | Company name              |
| rating        | DECIMAL(3,2)  | Average company rating    |
| price         | DECIMAL(10,2) | Price for services        |
| location      | VARCHAR(255)  | Company location          |

---

## Future Enhancements
- Integration of payment gateways.
- Addition of real-time location tracking.
- Personalized user recommendations.

---




