package com.hms.service.impl;

import com.hms.dao.DoctorDao;
import com.hms.dao.impl.DoctorDaoImpl;
import com.hms.entity.Doctor;
import com.hms.exception.BusinessException;
import com.hms.util.EmailUtil;

public class DoctorServiceImpl implements com.hms.service.DoctorService {

    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public Doctor registerDoctor(String name, String email, String specialization, double fee, int experienceYears) {
        if (fee < 500 || fee > 5000) {
            throw new BusinessException("Fee must be between 500 and 5000");
        }
        if (experienceYears < 1) {
            throw new BusinessException("Experience must be at least 1 year");
        }
        if (doctorDao.findByEmail(email) != null) {
            throw new BusinessException("Doctor email already exists");
        }

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setEmail(email);
        doctor.setSpecialization(specialization.toUpperCase());
        doctor.setConsultationFee(fee);
        doctor.setExperienceYears(experienceYears);
        doctor.setStatus("Available");

        doctorDao.save(doctor);

        String body = "Dear Dr. " + name + ",\nYour doctor ID is: " + doctor.getId() +
                "\nSpecialization: " + doctor.getSpecialization() +
                "\nFee: " + doctor.getConsultationFee();
        EmailUtil.sendEmail(email, "Doctor Onboarding", body);

        return doctor;
    }
}
