package com.hms.service;

import com.hms.entity.Patient;

public interface PatientService {

    Patient registerPatient(String name, String email, String contactNo, String rawPassword);

    Patient login(String email, String rawPassword);
}
