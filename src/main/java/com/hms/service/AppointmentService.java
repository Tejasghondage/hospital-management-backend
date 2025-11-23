package com.hms.service;

import com.hms.entity.Appointment;

import java.time.LocalDateTime;

public interface AppointmentService {

    Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime time);

    void confirmPayment(Long appointmentId, double amount);

    void cancelAppointment(Long appointmentId);
}
