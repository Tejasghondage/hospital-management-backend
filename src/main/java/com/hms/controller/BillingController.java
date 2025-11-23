package com.hms.controller;

import com.hms.entity.Bill;
import com.hms.service.BillingService;
import com.hms.service.impl.BillingServiceImpl;

public class BillingController {

    private final BillingService billingService = new BillingServiceImpl();

    public Bill generateBill(Long appointmentId, int patientAge, double baseAmount) {
        return billingService.generateBill(appointmentId, patientAge, baseAmount);
    }
}
