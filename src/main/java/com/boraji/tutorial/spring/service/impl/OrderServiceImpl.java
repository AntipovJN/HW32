package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.OrderDao;
import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.Order;
import com.boraji.tutorial.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {this.orderDao = orderDao;}

    @Transactional
    @Override
    public void addOrder(Code code, String address, String payment, Basket basket) {
        Order order = new Order(address, payment, code, basket);
        orderDao.addOrder(order);
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {

    }

    @Transactional
    @Override
    public void removeOrder(Order order) {

    }

    @Transactional
    @Override
    public Optional<Order> getById(long id) {
        return orderDao.getById(id);
    }

    @Transactional
    @Override
    public Optional<Order> getByCode(Code code) {
        return orderDao.getByCode(code);
    }

    @Transactional
    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

}
