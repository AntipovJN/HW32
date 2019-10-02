package com.boraji.tutorial.spring.dao.impl.Hibernate;

import com.boraji.tutorial.spring.dao.CodeDao;
import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class CodeDaoHibernate implements CodeDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(CodeDaoHibernate.class);

    @Autowired
    public CodeDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCode(Code code) {
        try {
            sessionFactory.getCurrentSession().save(code);
        } catch (HibernateException e) {
            logger.error(String.format("Failed adding code fpr user = '%s'", code.getUser()), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Code> getLastCodeForUser(User user) {
        try {
            TypedQuery<Code> query = sessionFactory.getCurrentSession().createQuery(
                    "FROM Code WHERE user = :user ORDER BY id DESC").setParameter("user", user);
            Code code = query.setMaxResults(1).getSingleResult();
            return Optional.of(code);
        } catch (HibernateException e) {
            logger.error(String.format("Failed get last code for user = '%s'", user.getId()), e);
            return Optional.empty();
        }
    }
}
