package com.hms.controller;

import com.hms.entity.Medicine;
import com.hms.service.InventoryService;
import com.hms.service.impl.InventoryServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class InventoryController {

    private final InventoryService inventoryService = new InventoryServiceImpl();

    public Medicine upsert(String name, int quantity, LocalDate expiryDate) {
        return inventoryService.upsertMedicine(name, quantity, expiryDate);
    }

    public void refreshStatuses() {
        inventoryService.refreshStatuses();
    }

    public List<Medicine> lowStock() {
        return inventoryService.getLowStockMedicines();
    }

    public List<Medicine> expired() {
        return inventoryService.getExpiredMedicines();
    }
}
