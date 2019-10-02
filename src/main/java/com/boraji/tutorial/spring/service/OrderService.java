package com.boraji.tutorial.spring.service;

import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addOrder(Code code, String address, String payment, Basket basket);

    Optional<Order> getById(long id);

    Optional<Order> getByCode(Code code);

    List<Order> getAll();

    void updateOrder(Order order);

    void removeOrder(Order order);
}
