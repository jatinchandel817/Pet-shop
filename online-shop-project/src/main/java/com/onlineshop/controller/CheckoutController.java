package com.onlineshop.controller;

import com.onlineshop.dto.CheckoutRequest;
import com.onlineshop.service.CartService;
import com.onlineshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (cartService.getItems().isEmpty()) return "redirect:/cart";
        model.addAttribute("checkoutRequest", new CheckoutRequest());
        model.addAttribute("total", cartService.getTotal());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute CheckoutRequest request,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("total", cartService.getTotal());
            return "checkout";
        }

        try {
            var order = orderService.placeOrder(request);
            return "redirect:/order-success?id=" + order.getId();
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("total", cartService.getTotal());
            return "checkout";
        }
    }

    @GetMapping("/order-success")
    public String success(@RequestParam Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order-success";
    }
}
