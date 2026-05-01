package ru.grigorev.tzh2o.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "monitors")
public class Monitor extends Product {
    @Column(nullable = false)
    private int diagonal;

    public Monitor() {
    }

    public Monitor(String serialNumber, String manufacturer, double price,
                   int stockQuantity, int diagonal) {
        super(serialNumber, manufacturer, price, stockQuantity);
        this.diagonal = diagonal;
    }

    public int getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(int diagonal) {
        this.diagonal = diagonal;
    }
}
