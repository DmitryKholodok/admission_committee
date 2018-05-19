package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.model.Specialty;
import by.issoft.kholodok.model.Subject;
import by.issoft.kholodok.model.user.User;
import org.apache.logging.log4j.Level;
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

    public void saveFaculty(Faculty faculty) {
        sessionFactory.getCurrentSession().persist(faculty);
    }

    public void saveSpecialty(Specialty specialty) {
        sessionFactory.getCurrentSession().persist(specialty);
    }

    public void updateFaculty(Faculty faculty) {
        sessionFactory.getCurrentSession().update(faculty);
    }

    public void updateSpecialty(Specialty specialty) {
        sessionFactory.getCurrentSession().update(specialty);
    }

    public void deleteFaculty(Faculty faculty) {
        sessionFactory.getCurrentSession().delete(faculty);
    }

    public void deleteSpecialty(Specialty specialty) {
        sessionFactory.getCurrentSession().delete(specialty);
    }

    public Faculty retrieveFacultyById(int id) {
        return sessionFactory.getCurrentSession().get(Faculty.class, new Integer(id));
    }

    public Specialty retrieveSpecialtyById(int id) {
        return sessionFactory.getCurrentSession().get(Specialty.class, new Integer(id));
    }

    public List<Faculty> retrieveSpecialtiesById(int facultyId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Specialty as sp " +
                        "where sp.faculty.id=" + facultyId)
                .getResultList();
    }
}
