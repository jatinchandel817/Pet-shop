package com.onlineshop.controller;

import com.onlineshop.dto.CheckoutRequest;
import com.onlineshop.entity.Order;
import com.onlineshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", service.findAll());
        return "orders";
    }

    @GetMapping("/api/products")
    @ResponseBody
    public String unused() {
        return "Use ProductApiController";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<Order> createApi(@Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(service.placeOrder(request));
    }

    @GetMapping("/api/orders")
    @ResponseBody
    public List<Order> apiOrders() {
        return service.findAll();
    }

    @GetMapping("/api/orders/{id}")
    @ResponseBody
    public Order apiOrder(@PathVariable Long id) {
        return service.findById(id);
    }
}
