package by.issoft.kholodok.controller;


import by.issoft.kholodok.controller.command.FindUsersByPageAmountCommand;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.util.RoleProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<User>> findUsers(
            @RequestParam int page,
            @RequestParam int amount) {
        ResponseEntity<List<User>> responseEntity;
        FindUsersByPageAmountCommand command = new FindUsersByPageAmountCommand(page, amount);
        List<User> userList = userService.findByRoleAndPageAmount(RoleProvider.getEnrolleeRole(), command);
        responseEntity = new ResponseEntity<>(userList, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/users/count")
    public ResponseEntity<Integer> getUsersCountByRole() {
        int usersCount= userService.getUsersCountByRole(RoleProvider.getEnrolleeRole());
        return new ResponseEntity<>(usersCount, HttpStatus.OK);
    }

}
