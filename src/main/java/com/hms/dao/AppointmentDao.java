package com.hms.dao;

import com.hms.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDao extends GenericDao<Appointment, Long> {

    boolean existsByDoctorAndTime(Long doctorId, LocalDateTime time);

    List<Appointment> findFutureAppointmentsByDoctor(Long doctorId, LocalDateTime fromTime);
}
