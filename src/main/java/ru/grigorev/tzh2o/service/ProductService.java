package ru.grigorev.tzh2o.service;

import ru.grigorev.tzh2o.dto.ProductDto;
import ru.grigorev.tzh2o.enums.ProductType;
import ru.grigorev.tzh2o.entity.*;
import ru.grigorev.tzh2o.enums.DesktopFormFactor;
import ru.grigorev.tzh2o.enums.LaptopSize;
import ru.grigorev.tzh2o.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto addProduct(ProductDto dto) {
        Product product = convertToEntity(dto);
        Product saved = productRepository.save(product);
        return convertToDto(saved);
    }

    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        updateEntity(existing, dto);
        Product updated = productRepository.save(existing);
        return convertToDto(updated);
    }

    public List<ProductDto> getProductsByType(ProductType type) {
        return productRepository.findAll().stream()
                .filter(product -> getProductType(product).equals(type))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToDto(product);
    }

    private ProductType getProductType(Product product) {
        if (product instanceof DesktopComputer) return ProductType.DESKTOP;
        if (product instanceof Laptop) return ProductType.LAPTOP;
        if (product instanceof Monitor) return ProductType.MONITOR;
        if (product instanceof HardDrive) return ProductType.HARD_DRIVE;
        throw new RuntimeException("Unknown product type");
    }

    private Product convertToEntity(ProductDto dto) {
        switch (dto.getType()) {
            case DESKTOP:
                DesktopComputer desktop = new DesktopComputer();
                desktop.setSerialNumber(dto.getSerialNumber());
                desktop.setManufacturer(dto.getManufacturer());
                desktop.setPrice(dto.getPrice());
                desktop.setStockQuantity(dto.getStockQuantity());
                desktop.setFormFactor(DesktopFormFactor.valueOf(dto.getFormFactor()));
                return desktop;

            case LAPTOP:
                Laptop laptop = new Laptop();
                laptop.setSerialNumber(dto.getSerialNumber());
                laptop.setManufacturer(dto.getManufacturer());
                laptop.setPrice(dto.getPrice());
                laptop.setStockQuantity(dto.getStockQuantity());
                laptop.setSize(LaptopSize.valueOf(dto.getLaptopSize()));
                return laptop;

            case MONITOR:
                Monitor monitor = new Monitor();
                monitor.setSerialNumber(dto.getSerialNumber());
                monitor.setManufacturer(dto.getManufacturer());
                monitor.setPrice(dto.getPrice());
                monitor.setStockQuantity(dto.getStockQuantity());
                monitor.setDiagonal(dto.getDiagonal());
                return monitor;

            case HARD_DRIVE:
                HardDrive hardDrive = new HardDrive();
                hardDrive.setSerialNumber(dto.getSerialNumber());
                hardDrive.setManufacturer(dto.getManufacturer());
                hardDrive.setPrice(dto.getPrice());
                hardDrive.setStockQuantity(dto.getStockQuantity());
                hardDrive.setCapacity(dto.getCapacity());
                return hardDrive;

            default:
                throw new RuntimeException("Unknown type: " + dto.getType());
        }
    }

    private void updateEntity(Product existing, ProductDto dto) {
        existing.setSerialNumber(dto.getSerialNumber());
        existing.setManufacturer(dto.getManufacturer());
        existing.setPrice(dto.getPrice());
        existing.setStockQuantity(dto.getStockQuantity());

        if (existing instanceof DesktopComputer && dto.getFormFactor() != null) {
            ((DesktopComputer) existing).setFormFactor(DesktopFormFactor.valueOf(dto.getFormFactor()));
        } else if (existing instanceof Laptop && dto.getLaptopSize() != null) {
            ((Laptop) existing).setSize(LaptopSize.valueOf(dto.getLaptopSize()));
        } else if (existing instanceof Monitor && dto.getDiagonal() != null) {
            ((Monitor) existing).setDiagonal(dto.getDiagonal());
        } else if (existing instanceof HardDrive && dto.getCapacity() != null) {
            ((HardDrive) existing).setCapacity(dto.getCapacity());
        }
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setManufacturer(product.getManufacturer());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setType(getProductType(product));

        if (product instanceof DesktopComputer) {
            dto.setFormFactor(((DesktopComputer) product).getFormFactor().name());
        } else if (product instanceof Laptop) {
            dto.setLaptopSize(((Laptop) product).getSize().name());
        } else if (product instanceof Monitor) {
            dto.setDiagonal(((Monitor) product).getDiagonal());
        } else if (product instanceof HardDrive) {
            dto.setCapacity(((HardDrive) product).getCapacity());
        }

        return dto;
    }
}