package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.controller.command.FindUsersByPageAmountCommand;
import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.dao.query.UserQueryProvider;
import by.issoft.kholodok.model.role.Role;
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

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

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

    @Transactional
    @Override
    public List<User> findByRoleAndPageAmount(Role role, FindUsersByPageAmountCommand pageAmount) {
        Query query =
                sessionFactory.getCurrentSession().createNativeQuery(
                        "select u.user_id, u.name, u.surname , u.email " +
                                "from user as u " +
                                "INNER JOIN user_role as ur " +
                                "on u.user_id = ur.user_id " +
                                "INNER JOIN role as r " +
                                "on ur.role_id = r.role_id " +
                                "where r.name = '" + role.getName() + "' " +
                                "ORDER BY u.user_id asc " +
                                "limit " + calculateOffset(pageAmount) + ", " + pageAmount.getAmount()
                );
        return (List<User>) query.getResultList();
    }

    @Transactional
    @Override
    public int getUsersCountByRole(Role role) {
        Query<BigInteger> query =
                sessionFactory.getCurrentSession().createNativeQuery(
                        "select count(*) " +
                                "from user_role " +
                                "inner join role " +
                                "on user_role.role_id = role.role_id " +
                                "where role.name = '" + role.getName() + "'"
                );
        return query.getSingleResult().intValue();
    }

    @Transactional
    @Override
    public List<User> findAllByPageAmount(FindUsersByPageAmountCommand command) {
        Query query =
                sessionFactory.getCurrentSession().createNativeQuery(
                        "select * " +
                                "from user " +
                                "ORDER BY user_id asc " +
                                "limit " + calculateOffset(command) + ", " + command.getAmount()
                );
        return (List<User>) query.getResultList();
    }

    @Transactional
    @Override
    public int getAllUsersCount() {
        Query<Long> query = sessionFactory.getCurrentSession().createQuery(userQueryProvider.getAllUsersCount());
        return query.uniqueResult().intValue();
    }

    @Transactional
    @Override
    public List<String> findEmailsByRole(Role role) {
        Query<String> query =
                sessionFactory.getCurrentSession().createNativeQuery(
                        "select u.email " +
                                "from user as u " +
                                "INNER JOIN user_role as ur " +
                                "on u.user_id = ur.user_id " +
                                "INNER JOIN role as r " +
                                "on ur.role_id = r.role_id " +
                                "where r.name = '" + role.getName() + "'"
                );
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<User> findBirthdayPersons() {
        Query<User> query =
                sessionFactory.getCurrentSession().createQuery(
                        "from User u " +
                                "where month(u.dateOfBirth) = month(current_date()) " +
                                "and day(u.dateOfBirth) = day(current_date())");
        return query.getResultList();
    }

    @Transactional
    @Override
    public User findByEmail(String email) {
        Query<User> query =
                sessionFactory.getCurrentSession().createQuery(
                        "from User u " +
                                "where u.email = '" + email + "'");
        // TODO change
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    private int calculateOffset(FindUsersByPageAmountCommand pageAmount) {
        return pageAmount.getPage() * pageAmount.getAmount() - pageAmount.getAmount();
    }

}
