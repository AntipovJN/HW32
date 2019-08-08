package com.boraji.tutorial.spring.service;

import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;

public interface BasketService {

    Basket getBasket(User user);

    void addProduct(User user, Product product);

    void removeProducts(User user);
}
