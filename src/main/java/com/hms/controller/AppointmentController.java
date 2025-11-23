package com.hms.controller;

import com.hms.entity.Appointment;
import com.hms.service.AppointmentService;
import com.hms.service.impl.AppointmentServiceImpl;

import java.time.LocalDateTime;

public class AppointmentController {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    public Appointment book(Long patientId, Long doctorId, LocalDateTime time) {
        return appointmentService.bookAppointment(patientId, doctorId, time);
    }

    public void confirmPayment(Long appointmentId, double amount) {
        appointmentService.confirmPayment(appointmentId, amount);
    }

    public void cancel(Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }
}
