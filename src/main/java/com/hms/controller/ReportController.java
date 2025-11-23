package com.hms.controller;

import com.hms.service.ReportService;
import com.hms.service.impl.ReportServiceImpl;

import java.time.YearMonth;

public class ReportController {

    private final ReportService reportService = new ReportServiceImpl();

    public void printMonthlyRevenue(YearMonth month) {
        reportService.printMonthlyRevenueReport(month);
    }
}
