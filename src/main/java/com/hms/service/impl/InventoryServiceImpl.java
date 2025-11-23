package com.hms.service.impl;

import com.hms.dao.MedicineDao;
import com.hms.dao.impl.MedicineDaoImpl;
import com.hms.entity.Medicine;
import com.hms.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;

public class InventoryServiceImpl implements com.hms.service.InventoryService {

    private final MedicineDao medicineDao = new MedicineDaoImpl();

    @Override
    public Medicine upsertMedicine(String name, int quantity, LocalDate expiryDate) {
        if (quantity < 0) {
            throw new BusinessException("Quantity cannot be negative");
        }
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new BusinessException("Expiry date cannot be in the past");
        }

        Medicine existing = medicineDao.findAll().stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        Medicine med;
        if (existing == null) {
            med = new Medicine();
            med.setName(name);
        } else {
            med = existing;
        }

        med.setQuantity(quantity);
        med.setExpiryDate(expiryDate);
        updateStatus(med);

        if (existing == null) {
            medicineDao.save(med);
        } else {
            medicineDao.update(med);
        }
        return med;
    }

    @Override
    public void refreshStatuses() {
        List<Medicine> all = medicineDao.findAll();
        for (Medicine m : all) {
            updateStatus(m);
            medicineDao.update(m);
        }
    }

    private void updateStatus(Medicine m) {
        if (m.getExpiryDate().isBefore(LocalDate.now())) {
            m.setStatus("Expired");
        } else if (m.getQuantity() < 10) {
            m.setStatus("Low Stock");
        } else {
            m.setStatus("Normal");
        }
    }

    @Override
    public List<Medicine> getLowStockMedicines() {
        return medicineDao.findLowStock();
    }

    @Override
    public List<Medicine> getExpiredMedicines() {
        return medicineDao.findExpired();
    }
}
