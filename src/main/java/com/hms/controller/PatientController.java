package com.hms.controller;

import com.hms.entity.Patient;
import com.hms.service.PatientService;
import com.hms.service.impl.PatientServiceImpl;

public class PatientController {

    private final PatientService patientService = new PatientServiceImpl();

    public Patient register(String name, String email, String contactNo, String password) {
        return patientService.registerPatient(name, email, contactNo, password);
    }

    public Patient login(String email, String password) {
        return patientService.login(email, password);
    }
}
