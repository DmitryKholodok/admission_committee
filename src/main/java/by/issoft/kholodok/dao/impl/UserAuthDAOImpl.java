package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.dao.UserAuthDAO;
import by.issoft.kholodok.dao.query.UserAuthQueryProvider;
import by.issoft.kholodok.model.user.UserAuth;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserAuthDAOImpl implements UserAuthDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserAuthQueryProvider userAuthDataQueryProvider;

    @Override
    @Transactional
    public UserAuth findByLogin(String login) {
        Query query =
                sessionFactory.getCurrentSession().createQuery(userAuthDataQueryProvider.findByLogin(login));
        return (UserAuth) query.uniqueResult();
    }

    @Override
    @Transactional
    public void update(UserAuth userAuthData) {
        sessionFactory.getCurrentSession().update(userAuthData);
    }
}
