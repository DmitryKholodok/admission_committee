package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.CreateEnrolleeDataCommand;
import by.issoft.kholodok.controller.command.UpdateEnrolleeDataCommand;
import by.issoft.kholodok.controller.command.mapper.UpdateEnrolleeDataMapper;
import by.issoft.kholodok.exception.BadUserRoleException;
import by.issoft.kholodok.exception.EnrolleeDataServiceException;
import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.service.EnrolleeDataService;
import by.issoft.kholodok.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/enrollees")
public class EnrolleeDataController {

    private static final Logger LOGGER = LogManager.getLogger(EnrolleeDataController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private EnrolleeDataService enrolleeDataService;

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
            EnrolleeData updatedEnrolleeData = enrolleeDataService.findById(id);
            if (updatedEnrolleeData == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                EnrolleeData enrolleeData = updateEnrolleeDataMapper.toEnrolleeData(command);
                enrolleeData.setId(id);
                enrolleeData.getBasicCertificate().setEnrolleeData(enrolleeData);
                enrolleeData.getSpecialtyEnrollee().setId(id);
                enrolleeData.getCertificates().forEach(x -> x.setEnrolleeData(enrolleeData));
                enrolleeDataService.update(enrolleeData);
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
                EnrolleeData enrolleeData = new EnrolleeData();
                enrolleeData.setId(command.getUserId());
                enrolleeDataService.save(enrolleeData);
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

}
