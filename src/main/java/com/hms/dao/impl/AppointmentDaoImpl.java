package com.hms.dao.impl;

import com.hms.config.HibernateUtil;
import com.hms.dao.AppointmentDao;
import com.hms.entity.Appointment;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDaoImpl extends GenericDaoImpl<Appointment, Long> implements AppointmentDao {

    public AppointmentDaoImpl() {
        super(Appointment.class);
    }

    @Override
    public boolean existsByDoctorAndTime(Long doctorId, LocalDateTime time) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "select count(a) from Appointment a where a.doctor.id = :docId and a.appointmentTime = :time",
                            Long.class)
                    .setParameter("docId", doctorId)
                    .setParameter("time", time)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    @Override
    public List<Appointment> findFutureAppointmentsByDoctor(Long doctorId, LocalDateTime fromTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Appointment a where a.doctor.id = :docId and a.appointmentTime >= :fromTime",
                            Appointment.class)
                    .setParameter("docId", doctorId)
                    .setParameter("fromTime", fromTime)
                    .list();
        }
    }
}
