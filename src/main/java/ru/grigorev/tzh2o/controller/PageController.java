package ru.grigorev.tzh2o.controller;

import ru.grigorev.tzh2o.dto.ProductDto;
import ru.grigorev.tzh2o.enums.ProductType;
import ru.grigorev.tzh2o.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) ProductType viewType,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long editId,
            @RequestParam(required = false) ProductType editType,
            Model model) {

        if (viewType != null) {
            model.addAttribute("products", productService.getProductsByType(viewType));
        }

        if (productId != null) {
            try {
                model.addAttribute("productById", productService.getProductById(productId));
            } catch (Exception e) {
                model.addAttribute("error", "Товар не найден");
            }
        }

        if (editId != null && editType != null) {
            model.addAttribute("editProduct", productService.getProductById(editId));
        }

        return "index";
    }

    @PostMapping("/add-desktop")
    public String addDesktop(
            @RequestParam String serialNumber,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam Integer stockQuantity,
            @RequestParam String formFactor,
            Model model) {
        try {
            ProductDto dto = new ProductDto();
            dto.setType(ProductType.DESKTOP);
            dto.setSerialNumber(serialNumber);
            dto.setManufacturer(manufacturer);
            dto.setPrice(price);
            dto.setStockQuantity(stockQuantity);
            dto.setFormFactor(formFactor);
            productService.addProduct(dto);
            model.addAttribute("message", "Товар добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/add-laptop")
    public String addLaptop(
            @RequestParam String serialNumber,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam Integer stockQuantity,
            @RequestParam String size,
            Model model) {
        try {
            ProductDto dto = new ProductDto();
            dto.setType(ProductType.LAPTOP);
            dto.setSerialNumber(serialNumber);
            dto.setManufacturer(manufacturer);
            dto.setPrice(price);
            dto.setStockQuantity(stockQuantity);
            dto.setLaptopSize(size);
            productService.addProduct(dto);
            model.addAttribute("message", "Товар добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/add-monitor")
    public String addMonitor(
            @RequestParam String serialNumber,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer diagonal,
            Model model) {
        try {
            ProductDto dto = new ProductDto();
            dto.setType(ProductType.MONITOR);
            dto.setSerialNumber(serialNumber);
            dto.setManufacturer(manufacturer);
            dto.setPrice(price);
            dto.setStockQuantity(stockQuantity);
            dto.setDiagonal(diagonal);
            productService.addProduct(dto);
            model.addAttribute("message", "Товар добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/add-harddrive")
    public String addHardDrive(
            @RequestParam String serialNumber,
            @RequestParam String manufacturer,
            @RequestParam double price,
            @RequestParam Integer stockQuantity,
            @RequestParam Integer capacity,
            Model model) {
        try {
            ProductDto dto = new ProductDto();
            dto.setType(ProductType.HARD_DRIVE);
            dto.setSerialNumber(serialNumber);
            dto.setManufacturer(manufacturer);
            dto.setPrice(price);
            dto.setStockQuantity(stockQuantity);
            dto.setCapacity(capacity);
            productService.addProduct(dto);
            model.addAttribute("message", "Товар добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/edit-product")
    public String editProduct(@ModelAttribute ProductDto dto, Model model) {
        try {
            productService.updateProduct(dto.getId(), dto);
            model.addAttribute("message", "Товар обновлен!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @GetMapping("/view/{type}")
    public String viewByType(@PathVariable ProductType type, Model model) {
        model.addAttribute("products", productService.getProductsByType(type));
        model.addAttribute("type", type);
        return "view";
    }

    @GetMapping("/view-id")
    public String viewById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("product", productService.getProductById(id));
        } catch (Exception e) {
            model.addAttribute("error", "Товар с ID " + id + " не найден");
        }
        return "view-id";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", "Ошибка: " + ex.getMessage());
        return "index";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        String fieldName = ex.getName();
        String message;

        switch (fieldName) {
            case "price":
                message = "Цена должна быть числом. Пример: 599.99";
                break;
            case "stockQuantity":
                message = "Количество должно быть целым числом. Пример: 10";
                break;
            case "diagonal":
                message = "Диагональ должна быть числом. Пример: 23.8";
                break;
            case "capacity":
                message = "Объем должен быть целым числом. Пример: 512";
                break;
            default:
                message = "Неверный формат поля '" + fieldName + "'. Введите число.";
        }

        model.addAttribute("error", message);
        return "index";
    }
}