package com.hms.dao;

import com.hms.entity.Medicine;

import java.util.List;

public interface MedicineDao extends GenericDao<Medicine, Long> {

    List<Medicine> findLowStock();

    List<Medicine> findExpired();
}
