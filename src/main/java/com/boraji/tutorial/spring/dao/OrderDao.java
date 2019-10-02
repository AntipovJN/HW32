package com.boraji.tutorial.spring.dao;

import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    void addOrder(Order order);

    Optional<Order> getById(long id);

    Optional<Order> getByCode(Code code);

    List<Order> getAll();

    void updateOrder(Order order);

    void removeOrder(Order order);

}
