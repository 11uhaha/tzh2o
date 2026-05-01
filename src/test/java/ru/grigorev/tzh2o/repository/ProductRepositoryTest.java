package ru.grigorev.tzh2o.repository;

import org.junit.jupiter.api.Assertions;
import ru.grigorev.tzh2o.TZH2OApplication;
import ru.grigorev.tzh2o.entity.DesktopComputer;
import ru.grigorev.tzh2o.entity.Product;
import ru.grigorev.tzh2o.enums.DesktopFormFactor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@DataJpaTest
@ContextConfiguration(classes = TZH2OApplication.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct_ShouldGenerateId() {
        DesktopComputer desktop = new DesktopComputer();
        desktop.setSerialNumber("SN999");
        desktop.setManufacturer("Test");
        desktop.setPrice(500.0);
        desktop.setStockQuantity(3);
        desktop.setFormFactor(DesktopFormFactor.DESKTOP);

        Product saved = productRepository.save(desktop);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("SN999", saved.getSerialNumber());
    }

    @Test
    void findById_ShouldReturnProduct() {
        DesktopComputer desktop = new DesktopComputer();
        desktop.setSerialNumber("SN888");
        desktop.setManufacturer("Test");
        desktop.setPrice(500.0);
        desktop.setStockQuantity(3);
        desktop.setFormFactor(DesktopFormFactor.DESKTOP);

        Product saved = productRepository.save(desktop);
        Product found = productRepository.findById(saved.getId()).orElse(null);

        Assertions.assertNotNull(found);
        Assertions.assertEquals("SN888", found.getSerialNumber());
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        productRepository.deleteAll();

        DesktopComputer desktop = new DesktopComputer();
        desktop.setSerialNumber("SN1");
        desktop.setManufacturer("A");
        desktop.setPrice(100.0);
        desktop.setStockQuantity(1);
        desktop.setFormFactor(DesktopFormFactor.DESKTOP);

        productRepository.save(desktop);

        List<Product> products = productRepository.findAll();

        Assertions.assertFalse(products.isEmpty());
    }
}