package com.onlineshop.controller;

import com.onlineshop.dto.CartAddRequest;
import com.onlineshop.dto.CartUpdateRequest;
import com.onlineshop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CartController {
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("items", service.getItems());
        model.addAttribute("total", service.getTotal());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam Long productId, @RequestParam(defaultValue = "1") Integer quantity) {
        service.add(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String update(@RequestParam Long id, @RequestParam Integer quantity) {
        service.update(id, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String remove(@PathVariable Long id) {
        service.remove(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clear() {
        service.clear();
        return "redirect:/cart";
    }

    @PostMapping("/api/cart/add")
    @ResponseBody
    public ResponseEntity<?> apiAdd(@Valid @RequestBody CartAddRequest request) {
        return ResponseEntity.ok(service.add(request.getProductId(), request.getQuantity()));
    }

    @GetMapping("/api/cart")
    @ResponseBody
    public ResponseEntity<?> apiCart() {
        return ResponseEntity.ok(Map.of("items", service.getItems(), "total", service.getTotal()));
    }

    @PutMapping("/api/cart/update/{id}")
    @ResponseBody
    public ResponseEntity<?> apiUpdate(@PathVariable Long id, @Valid @RequestBody CartUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request.getQuantity()));
    }

    @DeleteMapping("/api/cart/{id}")
    @ResponseBody
    public ResponseEntity<?> apiRemove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.ok(Map.of("message", "Cart item removed."));
    }
}
