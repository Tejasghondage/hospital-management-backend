package com.hms.dao.impl;

import com.hms.config.HibernateUtil;
import com.hms.dao.BillDao;
import com.hms.entity.Bill;
import org.hibernate.Session;

import java.time.YearMonth;
import java.util.List;

public class BillDaoImpl extends GenericDaoImpl<Bill, Long> implements BillDao {

    public BillDaoImpl() {
        super(Bill.class);
    }

    @Override
    public List<Bill> findByMonth(YearMonth month) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Bill b where year(b.generatedDate) = :year and month(b.generatedDate) = :month",
                            Bill.class)
                    .setParameter("year", month.getYear())
                    .setParameter("month", month.getMonthValue())
                    .list();
        }
    }
}
