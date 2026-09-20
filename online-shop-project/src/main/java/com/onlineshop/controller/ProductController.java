package com.onlineshop.controller;

import com.onlineshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("products", service.search(search));
        model.addAttribute("search", search == null ? "" : search);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.findById(id));
        return "product-details";
    }
}
