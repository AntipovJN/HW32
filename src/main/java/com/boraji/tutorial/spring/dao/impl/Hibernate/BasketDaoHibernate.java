package com.boraji.tutorial.spring.dao.impl.Hibernate;

import com.boraji.tutorial.spring.dao.BasketDao;
import com.boraji.tutorial.spring.entity.Basket;
import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class BasketDaoHibernate implements BasketDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(BasketDaoHibernate.class);

    @Autowired
    public BasketDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBasket(User user) {
        try {
            sessionFactory.getCurrentSession().save(new Basket(user, new ArrayList<>()));
        } catch (Exception e) {
            logger.error(String.format("Failed adding basket for user id ='%s'", user.getId()), e);
        }
    }

    @Override
    public Optional<Basket> getById(long id) {
        try {
            return Optional.ofNullable(
                    sessionFactory.getCurrentSession().get(Basket.class, id));
        } catch (Exception e) {
            logger.error(String.format("Failed get basket with id ='%s'", id), e);
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Basket> getLastBasketForUser(User user) {
        try {
            TypedQuery<Basket> query = sessionFactory.getCurrentSession().createQuery(
                    "FROM Basket WHERE user =:user ORDER BY id DESC ").setParameter("user", user);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            logger.error(String.format("Failed get basket for user id ='%s'", user.getId()), e);
            return Optional.empty();
        }
    }

    @Override
    public void addProduct(Basket basket, Product product) {
        try {
            basket.getProducts().add(product);
            sessionFactory.getCurrentSession().update(basket);
        } catch (Exception e) {
            logger.error(String.format("Failed adding product to basket basket for user id ='%s'",
                    basket.getUser().getId()), e);
        }
    }

    @Override
    public void removeBasket(User user) {
        Optional<Basket> optionalBasket = getLastBasketForUser(user);
        if (optionalBasket.isPresent()) {
            try {
                sessionFactory.getCurrentSession().remove(optionalBasket.get());
            } catch (Exception e) {
                logger.error(String.format("Failed remove basket for user id = '%s'", user.getId()), e);
            }
        }
    }
}
