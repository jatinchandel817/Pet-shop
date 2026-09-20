package com.onlineshop.service;

import com.onlineshop.entity.CartItem;
import com.onlineshop.entity.Product;
import com.onlineshop.exception.InsufficientStockException;
import com.onlineshop.exception.InvalidQuantityException;
import com.onlineshop.exception.ProductNotFoundException;
import com.onlineshop.repository.CartItemRepository;
import com.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> getItems() {
        return cartRepository.findAllByOrderByIdAsc();
    }

    public BigDecimal getTotal() {
        return getItems().stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public CartItem add(Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) throw new InvalidQuantityException("Quantity must be greater than zero.");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        CartItem item = cartRepository.findByProductId(productId).orElse(null);
        int newQuantity = quantity + (item == null ? 0 : item.getQuantity());

        if (newQuantity > product.getStockQuantity()) {
            throw new InsufficientStockException("Only " + product.getStockQuantity()
                    + " units of " + product.getName() + " are available.");
        }

        if (item == null) item = new CartItem(product, quantity);
        else item.setQuantity(newQuantity);

        return cartRepository.save(item);
    }

    @Transactional
    public CartItem update(Long id, Integer quantity) {
        if (quantity == null || quantity <= 0) throw new InvalidQuantityException("Quantity must be greater than zero.");
        CartItem item = cartRepository.findById(id)
                .orElseThrow(() -> new InvalidQuantityException("Cart item was not found."));
        if (quantity > item.getProduct().getStockQuantity()) {
            throw new InsufficientStockException("Only " + item.getProduct().getStockQuantity()
                    + " units are available.");
        }
        item.setQuantity(quantity);
        return cartRepository.save(item);
    }

    public void remove(Long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clear() {
        cartRepository.deleteAllInBatch();
    }
}
