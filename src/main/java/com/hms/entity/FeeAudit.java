package com.hms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_fee_audit")
public class FeeAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "old_fee")
    private double oldFee;

    @Column(name = "new_fee")
    private double newFee;

    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    public FeeAudit() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public double getOldFee() { return oldFee; }
    public void setOldFee(double oldFee) { this.oldFee = oldFee; }

    public double getNewFee() { return newFee; }
    public void setNewFee(double newFee) { this.newFee = newFee; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(LocalDateTime updatedOn) { this.updatedOn = updatedOn; }
}
