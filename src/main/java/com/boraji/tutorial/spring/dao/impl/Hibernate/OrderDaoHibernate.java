package com.boraji.tutorial.spring.dao.impl.Hibernate;

import com.boraji.tutorial.spring.dao.OrderDao;
import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.Order;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoHibernate implements OrderDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(OrderDaoHibernate.class);

    @Autowired
    public OrderDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addOrder(Order order) {
        try {
            sessionFactory.getCurrentSession().save(order);
        } catch (Exception e) {
            logger.error("Failed adding order", e);
        }
    }

    @Override
    public Optional<Order> getById(long id) {
        try {
            Order order = sessionFactory.getCurrentSession().get(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            logger.error(String.format("Failed get Order with id = '%s'", id), e);
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Order> getByCode(Code code) {
        try {
            TypedQuery<Order> query = sessionFactory.getCurrentSession().createQuery("FROM Order WHERE code =:code")
                    .setParameter("code", code);
            return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
        } catch (Exception e) {
            logger.error(String.format("Failed get Order with code = '%s'", code), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        try {
            return sessionFactory.getCurrentSession().createQuery("FROM Order").list();
        } catch (
                Exception e) {
            logger.error("Failed getting all from Orders", e);
        }
        return Collections.emptyList();
    }

    @Override
    public void updateOrder(Order order) {
        try {
            sessionFactory.getCurrentSession().update(order);
        } catch (Exception e) {
            logger.error(String.format("Failed update order with id = '%s'", order.getId()), e);
        }
    }

    @Override
    public void removeOrder(Order order) {
        try {
            sessionFactory.getCurrentSession().delete(order);
        } catch (Exception e) {
            logger.error(String.format("Failed delete order with id = '%s'", order.getId()), e);
        }
    }
}
