package com.boraji.tutorial.spring.service;

import com.boraji.tutorial.spring.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    void add(String name, String description, double price);

    Optional<Product> getById(Long id);

    void updateProduct(Long id, String name, String description, double price);

    void  removeProduct(Long id);
}
