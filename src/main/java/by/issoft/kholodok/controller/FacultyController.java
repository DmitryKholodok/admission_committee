package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.faculty.AddFacultyCommand;
import by.issoft.kholodok.controller.command.faculty.UpdateFacultyCommand;
import by.issoft.kholodok.controller.mapper.faculty.AddFacultyMapper;
import by.issoft.kholodok.controller.mapper.faculty.UpdateFacultyMapper;
import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.service.UniverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@RestController
@RequestMapping(value = "/api/faculties")
public class FacultyController {

    @Autowired
    private UniverService univerService;

    @Autowired
    private AddFacultyMapper addFacultyMapper;

    @Autowired
    private UpdateFacultyMapper updateFacultyMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AddFacultyCommand command) {
        univerService.saveFaculty(addFacultyMapper.toFaculty(command));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateFacultyCommand command, @PathVariable int id) {
        Faculty currFaculty = univerService.retrieveFacultyById(id);
        if (currFaculty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        univerService.updateFaculty(updateFacultyMapper.toFaculty(command));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Faculty currFaculty = univerService.retrieveFacultyById(id);
        if (currFaculty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        univerService.deleteFaculty(currFaculty);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Faculty> retrieveFaculties() {
        return univerService.retrieveFaculties();
    }

}
