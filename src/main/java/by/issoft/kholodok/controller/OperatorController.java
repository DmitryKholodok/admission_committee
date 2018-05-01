package by.issoft.kholodok.controller;


import by.issoft.kholodok.model.PageAmount;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.role.RoleEnum;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/1/18
 */

@RestController
@RequestMapping(value = "/api/operator")
public class OperatorController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(OperatorController.class);

    @RequestMapping(value = "/users")
    public ResponseEntity<List<User>> findUsers(@RequestBody @Valid PageAmount pageAmount, BindingResult bindingResult) {
        ResponseEntity<List<User>> responseEntity;
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<User> userList = userService.findByRoleAndPageAmount(retrieveEnrolleeRole(), pageAmount);
            responseEntity = new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/count")
    public ResponseEntity<Integer> getUsersCountByRole() {
        int usersCount= userService.getUsersCountByRole(retrieveEnrolleeRole());
        return new ResponseEntity<>(usersCount, HttpStatus.OK);
    }

    private Role retrieveEnrolleeRole() {
        Role role = new Role();
        role.setName(RoleEnum.ENROLLEE.getValue());
        return role;
    }

}
