package com.hms.dao.impl;

import com.hms.config.HibernateUtil;
import com.hms.dao.PatientDao;
import com.hms.entity.Patient;
import org.hibernate.Session;

public class PatientDaoImpl extends GenericDaoImpl<Patient, Long> implements PatientDao {

    public PatientDaoImpl() {
        super(Patient.class);
    }

    @Override
    public Patient findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Patient p where p.email = :email", Patient.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    @Override
    public Patient findByContactNo(String contactNo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Patient p where p.contactNo = :contact", Patient.class)
                    .setParameter("contact", contactNo)
                    .uniqueResult();
        }
    }
}
