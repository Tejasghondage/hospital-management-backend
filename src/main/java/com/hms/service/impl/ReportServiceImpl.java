package com.hms.service.impl;

import com.hms.dao.BillDao;
import com.hms.dao.impl.BillDaoImpl;
import com.hms.entity.Bill;

import java.time.YearMonth;
import java.util.List;

public class ReportServiceImpl implements com.hms.service.ReportService {

    private final BillDao billDao = new BillDaoImpl();

    @Override
    public void printMonthlyRevenueReport(YearMonth month) {
        List<Bill> bills = billDao.findByMonth(month);
        if (bills.isEmpty()) {
            System.out.println("No bills for " + month);
            return;
        }

        double totalRevenue = 0;
        double totalDiscount = 0;
        int totalPatients = bills.size();

        for (Bill b : bills) {
            totalRevenue += b.getTotal();
            totalDiscount += b.getDiscount();
        }

        double avgBill = totalRevenue / totalPatients;

        System.out.println("===== Revenue Report for " + month + " =====");
        System.out.println("Total Patients Treated: " + totalPatients);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("Average Bill Amount: " + avgBill);
        System.out.println("Total Discount Given: " + totalDiscount);
        System.out.println("============================================");
    }
}
