package ru.grigorev.tzh2o.entity;

import jakarta.persistence.*;
import ru.grigorev.tzh2o.enums.DesktopFormFactor;

@Entity
@Table(name = "desktop_computers")
public class DesktopComputer extends Product {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DesktopFormFactor formFactor;

    public DesktopComputer() {
    }

    public DesktopComputer(String serialNumber, String producer, double price,
                           int storageRemain, DesktopFormFactor formFactor) {
        super(serialNumber, producer, price, storageRemain);
        this.formFactor = formFactor;
    }

    public DesktopFormFactor getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(DesktopFormFactor formFactor) {
        this.formFactor = formFactor;
    }
}
