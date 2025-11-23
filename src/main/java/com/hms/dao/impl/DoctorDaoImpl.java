package com.hms.dao.impl;

import com.hms.config.HibernateUtil;
import com.hms.dao.DoctorDao;
import com.hms.entity.Doctor;
import org.hibernate.Session;

public class DoctorDaoImpl extends GenericDaoImpl<Doctor, Long> implements DoctorDao {

    public DoctorDaoImpl() {
        super(Doctor.class);
    }

    @Override
    public Doctor findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Doctor d where d.email = :email", Doctor.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }
}
