package com.hms.service.impl;

import com.hms.dao.AppointmentDao;
import com.hms.dao.BillDao;
import com.hms.dao.impl.AppointmentDaoImpl;
import com.hms.dao.impl.BillDaoImpl;
import com.hms.entity.Appointment;
import com.hms.entity.Bill;
import com.hms.exception.BusinessException;
import com.hms.util.EmailUtil;

import java.time.LocalDate;

public class BillingServiceImpl implements com.hms.service.BillingService {

    private final AppointmentDao appointmentDao = new AppointmentDaoImpl();
    private final BillDao billDao = new BillDaoImpl();

    @Override
    public Bill generateBill(Long appointmentId, int patientAge, double baseAmount) {
        Appointment appointment = appointmentDao.findById(appointmentId);
        if (appointment == null) {
            throw new BusinessException("Invalid appointment id");
        }
        if (!"Completed".equalsIgnoreCase(appointment.getStatus())) {
            throw new BusinessException("Bill can be generated only after appointment is completed");
        }

        Bill existing = billDao.findAll().stream()
                .filter(b -> b.getAppointment().getId().equals(appointmentId))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            throw new BusinessException("Bill already exists for this appointment");
        }

        double discount = 0;
        if (patientAge >= 60) {
            discount += baseAmount * 0.15;
        }
        if (baseAmount > 1000) {
            discount += baseAmount * 0.05;
        }
        double amountAfterDiscount = baseAmount - discount;
        double gst = amountAfterDiscount * 0.18;
        double total = amountAfterDiscount + gst;

        Bill bill = new Bill();
        bill.setAppointment(appointment);
        bill.setAmount(baseAmount);
        bill.setDiscount(discount);
        bill.setGst(gst);
        bill.setTotal(total);
        bill.setGeneratedDate(LocalDate.now());

        billDao.save(bill);

        EmailUtil.sendEmail(appointment.getPatient().getEmail(),
                "Bill Generated",
                "Your total bill is " + total + " (GST included).");

        return bill;
    }
}
