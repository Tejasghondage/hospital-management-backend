package com.hms.service;

import com.hms.entity.Doctor;

public interface DoctorService {

    Doctor registerDoctor(String name, String email, String specialization, double fee, int experienceYears);
}
