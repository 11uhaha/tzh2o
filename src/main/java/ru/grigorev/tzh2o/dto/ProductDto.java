package ru.grigorev.tzh2o.dto;

import ru.grigorev.tzh2o.enums.ProductType;

public class ProductDto {
    private Long id;
    private ProductType type;
    private String serialNumber;
    private String manufacturer;
    private double price;
    private Integer stockQuantity;
    private String formFactor;
    private String laptopSize;
    private Integer diagonal;
    private Integer capacity;

    public ProductDto() {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getFormFactor() { return formFactor; }
    public void setFormFactor(String formFactor) { this.formFactor = formFactor; }

    public String getLaptopSize() { return laptopSize; }
    public void setLaptopSize(String laptopSize) { this.laptopSize = laptopSize; }

    public Integer getDiagonal() { return diagonal; }
    public void setDiagonal(Integer diagonal) { this.diagonal = diagonal; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}