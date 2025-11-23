Hospital Management Backend - Core Java + Hibernate + MySQL

How to run:
1. Ensure you have MySQL running and create database `hms_db`.
2. Update username/password in src/main/resources/hibernate.cfg.xml if needed.
3. Import this project into Eclipse as Existing Maven Project.
4. Let Maven download dependencies (Hibernate + MySQL connector).
5. Run the main class:
   com.hms.frontend.HospitalManagementApplication

This console app simulates the use-cases from the Hospital Management Project Plan:
- Patient registration & login (with hashing, locking, email logs)
- Doctor registration
- Appointment booking, payment confirmation, cancellation with refund
- Bill generation with discount and GST
- Medicine inventory management (low stock, expiry)
- Monthly revenue report
