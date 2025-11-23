package com.hms.dao;

import com.hms.entity.Bill;

import java.time.YearMonth;
import java.util.List;

public interface BillDao extends GenericDao<Bill, Long> {

    List<Bill> findByMonth(YearMonth month);
}
