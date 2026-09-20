package com.onlineshop.repository;

import com.onlineshop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByOrderByIdAsc();
    Optional<CartItem> findByProductId(Long productId);
}
