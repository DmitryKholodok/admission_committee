package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.dao.RoleDAO;
import by.issoft.kholodok.dao.query.RoleQueryProvider;
import by.issoft.kholodok.model.role.Role;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RoleQueryProvider roleRequestProvider;

    @Override
    @Transactional
    public Role findRoleByName(String roleName) {
        Query query =
                sessionFactory.getCurrentSession()
                        .createQuery(roleRequestProvider.findRoleByName(roleName));
        return (Role) query.uniqueResult();
    }
}
