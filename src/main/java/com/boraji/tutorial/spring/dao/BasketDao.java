package com.boraji.tutorial.spring.dao;

import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;

import java.util.Optional;

public interface BasketDao {

    void addBasket(User user);

    Optional<Basket> getById(long id);

    Optional<Basket> getLastBasketForUser(User user);

    void addProduct(Basket basket, Product product);

    void removeBasket(User user);

}
