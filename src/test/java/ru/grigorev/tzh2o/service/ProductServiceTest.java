package ru.grigorev.tzh2o.service;

import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.grigorev.tzh2o.dto.ProductDto;
import ru.grigorev.tzh2o.enums.ProductType;
import ru.grigorev.tzh2o.entity.DesktopComputer;
import ru.grigorev.tzh2o.entity.Laptop;
import ru.grigorev.tzh2o.entity.Product;
import ru.grigorev.tzh2o.enums.DesktopFormFactor;
import ru.grigorev.tzh2o.enums.LaptopSize;
import ru.grigorev.tzh2o.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDto desktopDto;
    private DesktopComputer desktop;

    @BeforeEach
    void setUp() {
        desktopDto = new ProductDto();
        desktopDto.setType(ProductType.DESKTOP);
        desktopDto.setSerialNumber("SN123");
        desktopDto.setManufacturer("Samsung");
        desktopDto.setPrice(1000.0);
        desktopDto.setStockQuantity(5);
        desktopDto.setFormFactor("DESKTOP");

        desktop = new DesktopComputer();
        desktop.setId(1L);
        desktop.setSerialNumber("SN123");
        desktop.setManufacturer("Samsung");
        desktop.setPrice(1000.0);
        desktop.setStockQuantity(5);
        desktop.setFormFactor(DesktopFormFactor.DESKTOP);
    }

    @Test
    void addProduct_ShouldSaveAndReturnProduct() {
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(desktop);

        ProductDto result = productService.addProduct(desktopDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("SN123", result.getSerialNumber());
        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    void getProductById_WhenExists_ShouldReturnProduct() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(desktop));

        ProductDto result = productService.getProductById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("SN123", result.getSerialNumber());
    }

    @Test
    void getProductById_WhenNotExists_ShouldThrowException() {
        Mockito.when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> productService.getProductById(99L));
    }

    @Test
    void getProductsByType_ShouldReturnFilteredProducts() {
        Laptop laptop = new Laptop();
        laptop.setId(2L);
        laptop.setSerialNumber("LAP123");
        laptop.setManufacturer("Dell");
        laptop.setPrice(800.0);
        laptop.setStockQuantity(3);
        laptop.setSize(LaptopSize.INCH_14);

        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(desktop, laptop));

        List<ProductDto> result = productService.getProductsByType(ProductType.DESKTOP);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("SN123", result.get(0).getSerialNumber());
    }

    @Test
    void updateProduct_ShouldUpdateAndReturn() {
        ProductDto updateDto = new ProductDto();
        updateDto.setPrice(1200.0);
        updateDto.setStockQuantity(10);
        updateDto.setSerialNumber("SN123");
        updateDto.setManufacturer("Samsung");
        updateDto.setType(ProductType.DESKTOP);
        updateDto.setFormFactor("DESKTOP");

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(desktop));
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(desktop);

        ProductDto result = productService.updateProduct(1L, updateDto);

        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
    }
}