package com.hms.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private double amount;
    private double discount;
    private double total;
    private double gst;

    @Column(name = "generated_date")
    private LocalDate generatedDate;

    public Bill() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getGst() { return gst; }
    public void setGst(double gst) { this.gst = gst; }

    public LocalDate getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDate generatedDate) { this.generatedDate = generatedDate; }
}
