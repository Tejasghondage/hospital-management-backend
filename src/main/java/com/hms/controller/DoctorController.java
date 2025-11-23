package com.hms.controller;

import com.hms.entity.Doctor;
import com.hms.service.DoctorService;
import com.hms.service.impl.DoctorServiceImpl;

public class DoctorController {

    private final DoctorService doctorService = new DoctorServiceImpl();

    public Doctor register(String name, String email, String specialization, double fee, int experienceYears) {
        return doctorService.registerDoctor(name, email, specialization, fee, experienceYears);
    }
}
