package ru.grigorev.tzh2o.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hard_drives")
public class HardDrive extends Product {

    @Column(nullable = false)
    private int capacity;

    public HardDrive() {
    }

    public HardDrive(String serialNumber, String producer, double price,
                     int storageRemain, int capacity) {
        super(serialNumber, producer, price, storageRemain);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
