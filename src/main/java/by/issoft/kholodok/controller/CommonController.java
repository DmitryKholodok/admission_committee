package by.issoft.kholodok.controller;

import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.model.Subject;
import by.issoft.kholodok.service.UniverService;
import by.issoft.kholodok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@RestController
@RequestMapping(value = "/api")
public class CommonController {

    @Autowired
    private UniverService univerService;

    @GetMapping(value = "/faculties")
    public List<Faculty> retrieveFaculties() {
        return univerService.retrieveFaculties();
    }

    @GetMapping(value = "/subjects")
    public List<Subject> retrieveSubjects() {
        return univerService.retrieveSubjects();
    }

}
