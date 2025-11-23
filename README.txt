# 📚 Hospital Management Backend  
A simple and fully functional **Hospital Management System backend** built using:

- **Core Java**
- **Hibernate (ORM)**
- **MySQL Database**
- **Maven**
- **Layered Architecture**
- **OOP-based design**

This backend project handles patient management, doctor onboarding, appointments, billing, refunds, medicine inventory, and monthly revenue reports — all accessible through a **console-based frontend**.

## 🌟 Features

### 🧑‍⚕️ Patient Module
- New patient registration  
- Secure password hashing (SHA-256)  
- Login with failure tracking  
- Auto-lock account after 3 failed attempts  
- Login email notification (console simulated)  

### 👨‍⚕️ Doctor Module
- Doctor registration  
- Specialization & experience validation  
- Consultation fee rules  
- Email notification (console simulated)  

### 📅 Appointment Module
- Book appointment (only future slots allowed)  
- Prevent double booking for same doctor/time  
- Payment confirmation for appointments  
- Appointment cancellation with refund rules (80% refund if > 24 hours left)  

### 💳 Billing Module
- Bill generation after appointment completion  
- Automatic:
  - Discount based on age  
  - Discount based on treatment cost  
  - GST calculation  
- Sends invoice confirmation (console simulated)  

### 🧪 Inventory Module
- Add/update medicines  
- Track stock quantity  
- Auto-update status:
  - Normal  
  - Low Stock  
  - Expired  
- List expired / low stock medicines  

### 📊 Reporting Module
- Monthly revenue report  
- Shows:
  - Total patients  
  - Total revenue  
  - Average bill  
  - Total discounts  

## 🏗 Project Architecture

```
hospital-management-backend
│
├── src/main/java
│   └── com/hms
│       ├── config          # Hibernate configuration manager
│       ├── controller      # Controllers forwarding data to services
│       ├── dao             # Data Access Interfaces
│       ├── dao/impl        # Hibernate DAO implementations
│       ├── entity          # Database entity classes
│       ├── exception       # Custom exceptions
│       ├── frontend        # Console application (Main method)
│       ├── service         # Service layer interfaces
│       ├── service/impl    # Business Logic for each module
│       └── util            # Utility classes (Hashing, Email, Validation)
│
├── src/main/resources
│   └── hibernate.cfg.xml   # Database & Hibernate settings
│
├── pom.xml                 # Maven dependencies
└── README.md               # Project documentation
```

## 🛠 Tech Stack

| Technology | Purpose |
|-----------|----------|
| **Java 8+** | Core language |
| **Hibernate ORM** | Database mapping |
| **MySQL** | Main database |
| **Maven** | Dependency management |
| **Layered architecture** | Better maintainability |

## 🚀 How to Run This Project

### 1️⃣ **Set up the database**
Create database:

```sql
CREATE DATABASE hms_db;
```

Edit database username/password in:

```
src/main/resources/hibernate.cfg.xml
```

Set your MySQL credentials here:

```xml
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">root</property>
```

### 2️⃣ **Import project in Eclipse**

```
File → Import → Existing Maven Project
```

Let Maven download all dependencies.

### 3️⃣ **Run the Application**

Run this class:

```
com.hms.frontend.HospitalManagementApplication
```

Console menu will appear with all features (register, login, appointments, billing, inventory etc.)

## 📌 Important Business Rules (from Problem Statement)
- Patients must have unique email & contact number  
- Doctors must have valid experience and fee range  
- Appointment must be booked **at least 1 day ahead**  
- Refund only if cancellation happens **24 hours before appointment**  
- GST = **18%**  
- Discounts:
  - 15% discount for senior citizens (age ≥ 60)
  - 5% discount if base amount > 1000  

## 📬 Email Notifications (Simulated)
Actual emails are not sent — but printed to console using:

```
EmailUtil.sendEmail()
```

## 🔐 Security
- Passwords stored using **SHA-256 hashing**
- Account lockout system to prevent brute-force attacks
- Input validation for email, contact number, password strength

## 🧑‍💻 Author

**Tejas Ghondage**  
Java Full-Stack Developer  
GitHub: https://github.com/Tejasghondage
