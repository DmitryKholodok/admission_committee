package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.model.Subject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Repository
@Transactional
public class UniverDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Faculty> retrieveFaculties() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Faculty")
                .getResultList();
    }

    public List<Subject> retrieveSubjects() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Subject")
                .list();
    }
}
