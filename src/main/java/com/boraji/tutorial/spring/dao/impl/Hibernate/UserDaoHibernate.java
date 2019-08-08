package com.boraji.tutorial.spring.dao.impl.Hibernate;

import com.boraji.tutorial.spring.dao.UserDao;
import com.boraji.tutorial.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoHibernate implements UserDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(UserDaoHibernate.class);

    @Autowired
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        try {
            sessionFactory.openSession().save(user);
        } catch (Exception e) {
            logger.error("Try to add user was failed!", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
            users = query.getResultList();
        } catch (HibernateException e) {
            logger.error("Failed get list of users", e);
        }
        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<User> getByEmail(String email) {
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession()
                    .createQuery("FROM User WHERE email =:email");
            query.setParameter("email", email);
            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Try to get user by email was failed!", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            User user = sessionFactory.getCurrentSession().get(User.class, id);
            return Optional.of(user);
        } catch (HibernateException e) {
            logger.error(String.format("Failed get user by id = '%s'", id), e);
            return Optional.empty();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            sessionFactory.getCurrentSession().update(user);
        } catch (HibernateException e) {
            logger.error(String.format("Failed update user with id = '%d'", user.getId()));
        }
    }

    @Override
    public void removeUser(User user) {
        try {
            sessionFactory.getCurrentSession().delete(user);
        } catch (HibernateException e) {
            logger.error(String.format("Failed delete user with id = '%d'", user.getId()));
        }
    }
}
