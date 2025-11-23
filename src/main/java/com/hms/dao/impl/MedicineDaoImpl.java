package com.hms.dao.impl;

import com.hms.config.HibernateUtil;
import com.hms.dao.MedicineDao;
import com.hms.entity.Medicine;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class MedicineDaoImpl extends GenericDaoImpl<Medicine, Long> implements MedicineDao {

    public MedicineDaoImpl() {
        super(Medicine.class);
    }

    @Override
    public List<Medicine> findLowStock() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Medicine m where m.quantity < 10",
                            Medicine.class)
                    .list();
        }
    }

    @Override
    public List<Medicine> findExpired() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Medicine m where m.expiryDate < :today",
                            Medicine.class)
                    .setParameter("today", LocalDate.now())
                    .list();
        }
    }
}
