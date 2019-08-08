package com.boraji.tutorial.spring.dao.impl.Hibernate;

import com.boraji.tutorial.spring.dao.ProductDao;
import com.boraji.tutorial.spring.entity.Product;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoHibernate implements ProductDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(ProductDaoHibernate.class);

    @Autowired
    public ProductDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try  {
            products = sessionFactory.getCurrentSession().createQuery("FROM Product").list();
                    } catch (HibernateException e) {
            logger.error("Failed get list of products", e);
        }
        return products;
    }

    @Override
    public void addProduct(Product item) {
        try {
            sessionFactory.getCurrentSession().save(item);
        } catch (HibernateException e) {
                       logger.error(String.format("Failed adding product with name = '%s'", item.getName()), e);
        }
    }

    @Override
    public Optional<Product> getById(long id) {
        try {
            Product product = sessionFactory.getCurrentSession().get(Product.class, id);
            return Optional.ofNullable(product);
        } catch (HibernateException e) {
            logger.error(String.format("Failed get product by id = '%s'", id), e);
            return Optional.empty();
        }
    }

    @Override
    public void updateProduct(Product product) {

        Transaction transaction = null;
        try{
            sessionFactory.getCurrentSession().update(product);
        } catch (HibernateException e) {

            logger.error(String.format("Failed update product with id = '%d'", product.getId()));
        }
    }

    @Override
    public void removeProduct(Product product) {
                Transaction transaction = null;
        try  {
            sessionFactory.getCurrentSession().delete(product);
        } catch (HibernateException e) {

            logger.error(String.format("Failed delete product with id = '%d'", product.getId()));
        }
    }
}
