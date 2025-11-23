package com.hms.service;

import com.hms.entity.Medicine;

import java.time.LocalDate;
import java.util.List;

public interface InventoryService {

    Medicine upsertMedicine(String name, int quantity, LocalDate expiryDate);

    void refreshStatuses();

    List<Medicine> getLowStockMedicines();

    List<Medicine> getExpiredMedicines();
}
