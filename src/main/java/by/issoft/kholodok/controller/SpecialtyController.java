package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.faculty.AddSpecialtyCommand;
import by.issoft.kholodok.controller.command.faculty.UpdateSpecialtyCommand;
import by.issoft.kholodok.controller.mapper.AddSpecialtyMapper;
import by.issoft.kholodok.controller.mapper.UpdateSpecialtyMapper;
import by.issoft.kholodok.model.Faculty;
import by.issoft.kholodok.model.Specialty;
import by.issoft.kholodok.service.UniverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@RestController
@RequestMapping(value = "/api")
public class SpecialtyController {

    @Autowired
    private UniverService univerService;

    @Autowired
    private AddSpecialtyMapper addSpecialtyMapper;

    @Autowired
    private UpdateSpecialtyMapper updateSpecialtyMapper;

    @PostMapping(value = "/faculties/{facultyId}/specialties")
    public ResponseEntity<Void> save(
            @RequestBody @Valid AddSpecialtyCommand command,
            @PathVariable int facultyId) {
        Faculty faculty = univerService.retrieveFacultyById(facultyId);
        if (faculty == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Specialty specialty = addSpecialtyMapper.toSpecialty(command);
        specialty.setFaculty(faculty);
        univerService.saveSpecialty(specialty);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/specialties/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateSpecialtyCommand command, @PathVariable int id) {
        Specialty currSpecialty = univerService.retrieveSpecialtyById(id);
        if (currSpecialty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Specialty specialty = updateSpecialtyMapper.toSpecialty(command);
        specialty.setFaculty(currSpecialty.getFaculty());
        univerService.updateSpecialty(specialty);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/specialties/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Specialty currSpecialty = univerService.retrieveSpecialtyById(id);
        if (currSpecialty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        univerService.deleteSpecialty(currSpecialty);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/faculties/{facultyId}/specialties")
    public List<Faculty> retrieveSpecialtiesByFacultyId(@PathVariable int facultyId) {
        return univerService.retrieveSpecialtiesById(facultyId);
    }

}
