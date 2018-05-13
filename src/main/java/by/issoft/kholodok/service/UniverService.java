package by.issoft.kholodok.service;

import by.issoft.kholodok.dao.UniverDao;
import by.issoft.kholodok.model.Faculty;
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
}
