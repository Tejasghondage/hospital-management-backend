package com.hms.service.impl;

import com.hms.dao.AppointmentDao;
import com.hms.dao.DoctorDao;
import com.hms.dao.PatientDao;
import com.hms.dao.impl.AppointmentDaoImpl;
import com.hms.dao.impl.DoctorDaoImpl;
import com.hms.dao.impl.PatientDaoImpl;
import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.entity.Payment;
import com.hms.entity.Refund;
import com.hms.exception.BusinessException;
import com.hms.util.EmailUtil;
import com.hms.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Duration;
import java.time.LocalDateTime;

public class AppointmentServiceImpl implements com.hms.service.AppointmentService {

    private final AppointmentDao appointmentDao = new AppointmentDaoImpl();
    private final PatientDao patientDao = new PatientDaoImpl();
    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime time) {
        if (time.isBefore(LocalDateTime.now().plusDays(1))) {
            throw new BusinessException("Appointment must be at least 1 day in future");
        }

        Patient patient = patientDao.findById(patientId);
        Doctor doctor = doctorDao.findById(doctorId);

        if (patient == null || doctor == null) {
            throw new BusinessException("Invalid patient or doctor id");
        }

        if (appointmentDao.existsByDoctorAndTime(doctorId, time)) {
            throw new BusinessException("Slot already booked for this doctor");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(time);
        appointment.setStatus("Scheduled");
        appointment.setFeesPaid(false);

        appointmentDao.save(appointment);

        String patientBody = "Appointment booked with Dr. " + doctor.getName() +
                " on " + time + " Fee: " + doctor.getConsultationFee();
        EmailUtil.sendEmail(patient.getEmail(), "Appointment Confirmation (Pending Payment)", patientBody);

        String doctorBody = "New appointment booked with patient: " + patient.getName() +
                " at " + time;
        EmailUtil.sendEmail(doctor.getEmail(), "New Appointment Slot", doctorBody);

        return appointment;
    }

    @Override
    public void confirmPayment(Long appointmentId, double amount) {
        Appointment appointment = appointmentDao.findById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("Invalid appointment id");
        }
        Doctor doctor = appointment.getDoctor();
        if (amount != doctor.getConsultationFee()) {
            throw new BusinessException("Payment amount must equal consultation fee");
        }
        if (!"Scheduled".equalsIgnoreCase(appointment.getStatus())) {
            throw new BusinessException("Only scheduled appointments can be paid");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Payment payment = new Payment();
            payment.setAppointment(appointment);
            payment.setAmount(amount);
            payment.setPaymentTime(LocalDateTime.now());
            session.save(payment);

            appointment.setFeesPaid(true);
            appointment.setStatus("Confirmed");
            session.update(appointment);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        EmailUtil.sendEmail(appointment.getPatient().getEmail(),
                "Appointment Confirmed",
                "Your payment is received and appointment is confirmed.");
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentDao.findById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("Invalid appointment id");
        }
        if (!"Confirmed".equalsIgnoreCase(appointment.getStatus())) {
            throw new BusinessException("Only confirmed appointments can be cancelled with refund logic");
        }

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(now, appointment.getAppointmentTime());
        double refundAmount = 0;
        boolean eligible = diff.toHours() >= 24;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            if (eligible) {
                double paid = appointment.getDoctor().getConsultationFee();
                refundAmount = paid * 0.8;
                Refund refund = new Refund();
                refund.setAppointment(appointment);
                refund.setAmount(refundAmount);
                refund.setRefundedOn(LocalDateTime.now());
                session.save(refund);
            }
            appointment.setStatus("Cancelled");
            session.update(appointment);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        String msg = "Your appointment has been cancelled. ";
        if (eligible) {
            msg += "Refund Amount: " + refundAmount;
        } else {
            msg += "No refund as cancellation is within 24 hours of appointment.";
        }

        EmailUtil.sendEmail(appointment.getPatient().getEmail(), "Appointment Cancelled", msg);
        EmailUtil.sendEmail(appointment.getDoctor().getEmail(), "Appointment Cancelled",
                "Appointment with patient " + appointment.getPatient().getName() + " is cancelled.");
    }
}
