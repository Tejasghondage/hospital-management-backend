package com.hms.service;

import com.hms.entity.Bill;

public interface BillingService {

    Bill generateBill(Long appointmentId, int patientAge, double baseAmount);
}
