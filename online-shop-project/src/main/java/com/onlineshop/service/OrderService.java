package com.onlineshop.service;

import com.onlineshop.dto.CheckoutRequest;
import com.onlineshop.entity.*;
import com.onlineshop.exception.InsufficientStockException;
import com.onlineshop.exception.OrderNotFoundException;
import com.onlineshop.repository.CartItemRepository;
import com.onlineshop.repository.OrderRepository;
import com.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository, CartItemRepository cartRepository,
                        ProductRepository productRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    @Transactional
    public Order placeOrder(CheckoutRequest request) {
        List<CartItem> cartItems = cartRepository.findAllByOrderByIdAsc();
        if (cartItems.isEmpty()) throw new IllegalStateException("Your cart is empty.");

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());
        customer = customerService.saveOrUpdate(customer);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new IllegalStateException("A product in your cart no longer exists."));

            if (cartItem.getQuantity() > product.getStockQuantity()) {
                throw new InsufficientStockException("Insufficient stock for " + product.getName() + ".");
            }

            OrderItem orderItem = new OrderItem(product, cartItem.getQuantity(), product.getPrice());
            order.addItem(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        cartRepository.deleteAllInBatch();
        return saved;
    }

    public List<Order> findAll() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
