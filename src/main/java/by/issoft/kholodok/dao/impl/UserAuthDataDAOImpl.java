package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.dao.UserAuthDataDAO;
import by.issoft.kholodok.dao.query.UserAuthDataQueryProvider;
import by.issoft.kholodok.model.UserAuthData;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserAuthDataDAOImpl implements UserAuthDataDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserAuthDataQueryProvider userAuthDataQueryProvider;

    @Override
    @Transactional
    public UserAuthData findByLogin(String login) {
        Query query =
                sessionFactory.getCurrentSession().createQuery(userAuthDataQueryProvider.findByLogin(login));
        return (UserAuthData) query.uniqueResult();
    }

    @Override
    @Transactional
    public void update(UserAuthData userAuthData) {
        sessionFactory.getCurrentSession().update(userAuthData);
    }
}
