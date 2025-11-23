package com.hms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private double amount;

    @Column(name = "refunded_on")
    private LocalDateTime refundedOn;

    public Refund() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getRefundedOn() { return refundedOn; }
    public void setRefundedOn(LocalDateTime refundedOn) { this.refundedOn = refundedOn; }
}
