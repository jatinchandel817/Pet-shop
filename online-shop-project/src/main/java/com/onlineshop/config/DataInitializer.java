package com.onlineshop.config;

import com.onlineshop.entity.Product;
import com.onlineshop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository repository;

    public DataInitializer(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        repository.saveAll(List.of(
            product("Laptop", "Powerful laptop for work and study.", "129999", "https://placehold.co/600x400?text=Laptop", "Electronics", 10),
            product("Smartphone", "Modern smartphone with a bright display.", "49999", "https://placehold.co/600x400?text=Smartphone", "Electronics", 20),
            product("Headphones", "Comfortable wireless headphones.", "2999", "https://placehold.co/600x400?text=Headphones", "Audio", 30),
            product("Keyboard", "Mechanical keyboard for everyday typing.", "2499", "https://placehold.co/600x400?text=Keyboard", "Accessories", 25),
            product("Mouse", "Ergonomic wireless mouse.", "1299", "https://placehold.co/600x400?text=Mouse", "Accessories", 40),
            product("Smart Watch", "Fitness and notification smart watch.", "5999", "https://placehold.co/600x400?text=Smart+Watch", "Wearables", 15),
            product("Backpack", "Durable laptop backpack.", "1999", "https://placehold.co/600x400?text=Backpack", "Lifestyle", 18),
            product("T-Shirt", "Comfortable everyday cotton T-shirt.", "799", "https://placehold.co/600x400?text=T-Shirt", "Fashion", 50),
            product("Shoes", "Lightweight casual shoes.", "2499", "https://placehold.co/600x400?text=Shoes", "Fashion", 22),
            product("Bluetooth Speaker", "Portable speaker with clear sound.", "3499", "https://placehold.co/600x400?text=Bluetooth+Speaker", "Audio", 16)
        ));
    }

    private Product product(String name, String description, String price, String image,
                            String category, int stock) {
        return new Product(name, description, new BigDecimal(price), image, category, stock);
    }
}
