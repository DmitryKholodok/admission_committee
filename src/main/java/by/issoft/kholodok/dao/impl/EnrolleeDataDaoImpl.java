package by.issoft.kholodok.dao.impl;

import by.issoft.kholodok.dao.EnrolleeDataDao;
import by.issoft.kholodok.model.Certificate;
import by.issoft.kholodok.model.EnrolleeData;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EnrolleeDataDaoImpl implements EnrolleeDataDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public EnrolleeData findById(int id) {
        return sessionFactory.getCurrentSession().get(EnrolleeData.class, new Integer(id));
    }

    @Override
    public void update(EnrolleeData enrolleeData) {
        sessionFactory.getCurrentSession().merge(enrolleeData);
    }

    @Override
    public void remove(EnrolleeData enrolleeData) {
        sessionFactory.getCurrentSession().remove(enrolleeData);
    }

    @Override
    public void save(EnrolleeData enrolleeData) {
        sessionFactory.getCurrentSession().persist(enrolleeData);
    }

    @Override
    public void remove(Certificate certificate) {
        sessionFactory.getCurrentSession().remove(certificate);
    }

}
