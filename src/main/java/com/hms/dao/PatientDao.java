package com.hms.dao;

import com.hms.entity.Patient;

public interface PatientDao extends GenericDao<Patient, Long> {

    Patient findByEmail(String email);

    Patient findByContactNo(String contactNo);
}
