package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.enrollee.CreateEnrolleeDataCommand;
import by.issoft.kholodok.controller.command.enrollee.UpdateEnrolleeDataCommand;
import by.issoft.kholodok.controller.mapper.enrollee.AddEnrolleeDataMapper;
import by.issoft.kholodok.controller.mapper.enrollee.UpdateEnrolleeDataMapper;
import by.issoft.kholodok.exception.BadUserRoleException;
import by.issoft.kholodok.exception.EnrolleeDataServiceException;
import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.service.EnrolleeDataService;
import by.issoft.kholodok.service.RoleService;
import by.issoft.kholodok.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping(value = "/api/enrollees")
public class EnrolleeDataController {

    private static final Logger LOGGER = LogManager.getLogger(EnrolleeDataController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private EnrolleeDataService enrolleeDataService;

    @Autowired
    private AddEnrolleeDataMapper addEnrolleeDataMapper;

    @Autowired
    private UpdateEnrolleeDataMapper updateEnrolleeDataMapper;

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateEnrolleeData(
            @PathVariable int id,
            @RequestBody @Valid UpdateEnrolleeDataCommand command,
            BindingResult bindingResult) {

        ResponseEntity<Void> responseEntity;
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            EnrolleeData currEd = enrolleeDataService.findById(id);
            if (currEd == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                EnrolleeData newEd = updateEnrolleeDataMapper.toEnrolleeData(command);
                enrolleeDataService.update(currEd, newEd);
                responseEntity = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EnrolleeData> findEnrolleeData(@PathVariable int id) {
        ResponseEntity<EnrolleeData> responseEntity;
        EnrolleeData enrolleeData = enrolleeDataService.findById(id);
        if (enrolleeData == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(enrolleeData, HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEnrolleeData(@PathVariable int id) {
        ResponseEntity<Void> responseEntity;
        try {
            EnrolleeData enrolleeData = enrolleeDataService.findById(id);
            if (enrolleeData == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Role currUserRole = roleService.retrieveUserRole(SecurityContextHolder.getContext().getAuthentication());
                if (roleService.isRoleAdmin(currUserRole)) {
                    enrolleeDataService.remove(enrolleeData);
                    responseEntity = new ResponseEntity<>(HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
        } catch (RoleServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Void> addEnrolleeData(
            @RequestBody @Valid CreateEnrolleeDataCommand command,
            BindingResult bindingResult) {

        ResponseEntity<Void> responseEntity;
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                EnrolleeData newEd = addEnrolleeDataMapper.toEnrolleeData(command);
                newEd.getCertificates().forEach(x -> x.setEnrolleeData(newEd));
                enrolleeDataService.save(newEd);
                responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (EnrolleeDataServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (BadUserRoleException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/exists", method = RequestMethod.HEAD)
    public ResponseEntity<Void> isEnrolleeDataExists(@RequestParam int id) {
        if (enrolleeDataService.findById(id) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
