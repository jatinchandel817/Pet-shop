package com.onlineshop.controller;

import com.onlineshop.entity.Product;
import com.onlineshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
    private final ProductService service;

    public ProductApiController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> all(@RequestParam(required = false) String search) {
        return service.search(search);
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) {
        return service.findById(id);
    }
}
