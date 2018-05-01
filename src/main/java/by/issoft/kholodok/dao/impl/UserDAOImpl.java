package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.dao.query.UserQueryProvider;
import by.issoft.kholodok.model.user.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserQueryProvider userQueryProvider;

    @Transactional
    @Override
    public User findById(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Transactional
    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        Query query =
                sessionFactory.getCurrentSession().createQuery(userQueryProvider.findByLogin(login));
        return (User) query.uniqueResult();
    }

    @Transactional
    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Transactional
    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Object obj = session.get(User.class, id);
        if (obj != null) {
            session.delete(obj);
            LOGGER.log(Level.INFO, "The user with id " + id + " has been removed!");
            return true;
        } else {
            LOGGER.log(Level.ERROR, "The user with id " + id + " was not found! ");
            return false;
        }

    }

    @Transactional
    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

}
