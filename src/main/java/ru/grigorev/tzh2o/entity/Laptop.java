package ru.grigorev.tzh2o.entity;

import jakarta.persistence.*;
import ru.grigorev.tzh2o.enums.LaptopSize;

@Entity
@Table(name = "laptops")
public class Laptop extends Product {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LaptopSize size;

    public Laptop() {
    }

    public Laptop(String serialNumber, String producer, double price,
                  int storageRemain, LaptopSize laptopSize) {
        super(serialNumber, producer, price, storageRemain);
        this.size = laptopSize;
    }

    public LaptopSize getSize() {
        return size;
    }

    public void setSize(LaptopSize size) {
        this.size = size;
    }
}
