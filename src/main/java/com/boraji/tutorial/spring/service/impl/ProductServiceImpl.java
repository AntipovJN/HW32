package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.ProductDao;
import com.boraji.tutorial.spring.entity.Product;
import com.boraji.tutorial.spring.service.ProductService;
import com.boraji.tutorial.spring.utils.IdGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = Logger.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    @Override
    public void add(String name, String description, double price)
            throws IllegalArgumentException, NumberFormatException {
        validateProductData(name, description, price);
        productDao.addProduct(new Product(IdGenerator.getItemId(), name, description, price));
    }

    @Transactional
    @Override
    public void updateProduct(Long id, String name, String description, double price)
            throws IllegalArgumentException, NumberFormatException {
        validateProductData(name, description, price);
        productDao.updateProduct(new Product(id, name, description, price));
    }

    @Transactional
    @Override
    public void removeProduct(Long id) {
        if (productDao.getById(id).isPresent()) {
            productDao.removeProduct(productDao.getById(id).get());
        } else {
            log.error(String.format("Failed remove product with id = '%s'. It is not exist", id));
        }
    }

    @Transactional
    @Override
    public Optional<Product> getById(Long id) {
        return productDao.getById(id);
    }

    @Transactional
    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }


    private void validateProductData(String name, String description, double price)
            throws IllegalArgumentException, NumberFormatException {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Name field cant be empty");
        }
        if (Objects.isNull(description)
                || description.isEmpty()) {
            throw new IllegalArgumentException("Description field cant be empty");
        }
        if (price < 0) {
            throw new NumberFormatException("Price must be biggest than 0");
        }
    }
}
