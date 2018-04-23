package by.issoft.kholodok.controller;

import by.issoft.kholodok.exception.AuthServiceException;
import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.model.User;
import by.issoft.kholodok.service.AuthService;
import by.issoft.kholodok.service.RightsValidator;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final static Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        ResponseEntity<Void> responseEntity;
        try {
            if (userValidator.isValidUser(user)) {
                LOGGER.info("Adding a new  user");
                userService.save(user);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
                responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
            } else {
                LOGGER.error( "User has invalid fields");
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (UserServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<User> findUser(@PathVariable int id) {

        ResponseEntity<User> responseEntity;
        try {
            User user = userService.findById(id);
            if (user != null) {
                if (authService.isClientCanGetUserData(user)) {
                    responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (AuthServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        ResponseEntity<Void> responseEntity;
        try {
            RoleEnum userRoleEnum = authService.retrieveUserRoleEnum(SecurityContextHolder.getContext().getAuthentication());
            if (!authService.isUserAdmin(userRoleEnum)) {
                responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                LOGGER.debug("Deleting the user with id: {}", id);
                boolean isUserDeleted = userService.deleteById(id);
                responseEntity = (isUserDeleted) ?
                        new ResponseEntity<>(HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (AuthServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {

        ResponseEntity<Void> responseEntity;
        try {
            if (userValidator.isValidUser(user)) {
                User updatedUser = userService.findById(id);
                if (updatedUser == null) {
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    org.springframework.security.core.userdetails.User principal =
                            (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                    // Whether the user updates himself
                    User currUser = userService.findByLogin(principal.getUsername());
                    if (currUser.getId() != id) {

                        // if the user updates not himself, we should validate his rights
                        if (!authService.isClientCanGetUserData(user)) {
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    }

                    user.setId(id);
                    userService.update(user);
                    responseEntity = new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                LOGGER.error("User has invalid fields");
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (AuthServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        ResponseEntity<List<User>> responseEntity;
        try {
            RoleEnum userRoleEnum = authService.retrieveUserRoleEnum(SecurityContextHolder.getContext().getAuthentication());
            if (authService.isUserAdmin(userRoleEnum)) {
                List<User> userList = userService.findAll();
                responseEntity = new ResponseEntity<>(userList, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        catch (AuthServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

}
