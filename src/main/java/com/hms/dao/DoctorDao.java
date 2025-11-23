package com.hms.dao;

import com.hms.entity.Doctor;

public interface DoctorDao extends GenericDao<Doctor, Long> {

    Doctor findByEmail(String email);
}
