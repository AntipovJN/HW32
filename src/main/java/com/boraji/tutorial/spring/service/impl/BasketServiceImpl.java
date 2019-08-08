package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.BasketDao;
import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketDao basketDao;

    @Autowired
    public BasketServiceImpl(BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    @Transactional
    @Override
    public void addProduct(User user, Product product) {
        if (!basketDao.getLastBasketForUser(user).isPresent()) {
            basketDao.addBasket(user);
        }
        Basket basket = basketDao.getLastBasketForUser(user).get();
        basketDao.addProduct(basket, product);
    }
    
    @Transactional
    @Override
    public void removeProducts(User user) {
        basketDao.addBasket(user);
    }

    @Transactional
    @Override
    public Basket getBasket(User user) {
        return basketDao.getLastBasketForUser(user).get();
    }


}
