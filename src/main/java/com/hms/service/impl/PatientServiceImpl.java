package com.hms.service.impl;

import com.hms.dao.PatientDao;
import com.hms.dao.impl.PatientDaoImpl;
import com.hms.entity.Patient;
import com.hms.exception.BusinessException;
import com.hms.util.EmailUtil;
import com.hms.util.EncryptionUtil;
import com.hms.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientServiceImpl implements com.hms.service.PatientService {

    private final PatientDao patientDao = new PatientDaoImpl();

    @Override
    public Patient registerPatient(String name, String email, String contactNo, String rawPassword) {
        if (!ValidationUtil.isValidEmail(email)) {
            throw new BusinessException("Invalid email format");
        }
        if (!ValidationUtil.isValidContact(contactNo)) {
            throw new BusinessException("Contact number must be 10 digits");
        }
        if (!ValidationUtil.isStrongPassword(rawPassword)) {
            throw new BusinessException("Password not strong enough");
        }
        if (patientDao.findByEmail(email) != null) {
            throw new BusinessException("Email already exists");
        }
        if (patientDao.findByContactNo(contactNo) != null) {
            throw new BusinessException("Contact already exists");
        }

        Patient patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);
        patient.setContactNo(contactNo);
        patient.setPasswordHash(EncryptionUtil.hashPassword(rawPassword));
        patient.setRegistrationDate(LocalDate.now());
        patient.setStatus("Active");
        patient.setFailedLoginAttempts(0);

        patientDao.save(patient);

        String body = "Welcome " + name + "\nYour patient ID is: " + patient.getId() +
                "\nUse your email and password to login. Keep it safe.";
        EmailUtil.sendEmail(email, "Welcome to HMS", body);

        return patient;
    }

    @Override
    public Patient login(String email, String rawPassword) {
        Patient patient = patientDao.findByEmail(email);
        if (patient == null) {
            throw new BusinessException("No patient found with email");
        }

        if (patient.getAccountLockedUntil() != null
                && patient.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessException("Account is locked. Try again later.");
        }

        String hashed = EncryptionUtil.hashPassword(rawPassword);
        if (!hashed.equals(patient.getPasswordHash())) {
            int attempts = patient.getFailedLoginAttempts() + 1;
            patient.setFailedLoginAttempts(attempts);

            if (attempts >= 3) {
                patient.setAccountLockedUntil(LocalDateTime.now().plusMinutes(15));
                patient.setStatus("Locked");
                EmailUtil.sendEmail(patient.getEmail(), "Account Locked",
                        "Your account has been locked due to multiple failed logins.");
            }

            patientDao.update(patient);
            throw new BusinessException("Invalid credentials");
        }

        patient.setFailedLoginAttempts(0);
        patient.setAccountLockedUntil(null);
        patient.setStatus("Active");
        patient.setLastLogin(LocalDateTime.now());
        patientDao.update(patient);

        EmailUtil.sendEmail(patient.getEmail(), "New Login Detected",
                "You have successfully logged into your HMS account.");

        return patient;
    }
}
