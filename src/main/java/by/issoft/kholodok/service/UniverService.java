package by.issoft.kholodok.service;

import by.issoft.kholodok.dao.UniverDao;
import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.model.Specialty;
import by.issoft.kholodok.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Service
public class UniverService {

    @Autowired
    private UniverDao univerDao;

    public List<Faculty> retrieveFaculties() {
        return univerDao.retrieveFaculties();
    }

    public List<Subject> retrieveSubjects() {
        return univerDao.retrieveSubjects();
    }

    public void saveFaculty(Faculty faculty) {
        univerDao.saveFaculty(faculty);
    }

    public void saveSpecialty(Specialty specialty) {
        univerDao.saveSpecialty(specialty);
    }

    public void updateFaculty(Faculty faculty) {
        univerDao.updateFaculty(faculty);
    }

    public void updateSpecialty(Specialty specialty) {
        univerDao.updateSpecialty(specialty);
    }

    public void deleteFaculty(Faculty faculty) {
        univerDao.deleteFaculty(faculty);
    }

    public void deleteSpecialty(Specialty specialty) {
        univerDao.deleteSpecialty(specialty);
    }

    public Faculty retrieveFacultyById(int id) {
        return univerDao.retrieveFacultyById(id);
    }

    public Specialty retrieveSpecialtyById(int id) {
        return univerDao.retrieveSpecialtyById(id);
    }

    public List<Specialty> retrieveSpecialtiesById(int facultyId) {
        return univerDao.retrieveSpecialtiesById(facultyId);
    }
}
