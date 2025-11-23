package com.hms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private double amount;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    public Payment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
}
