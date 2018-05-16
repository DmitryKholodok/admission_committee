package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.SignUpCommand;
import by.issoft.kholodok.controller.command.UpdateUserCommand;
import by.issoft.kholodok.controller.command.mapper.SignUpCommandMapper;
import by.issoft.kholodok.controller.command.mapper.UpdateUserCommandMapper;
import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.service.RoleService;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final static Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignUpCommandMapper signUpCommandMapper;

    @Autowired
    private UpdateUserCommandMapper updateUserCommandMapper;

    //GOOD
    @PostMapping
    public ResponseEntity<Void> addUser(@Valid @RequestBody SignUpCommand command, BindingResult bindingResult) {
        ResponseEntity<Void> responseEntity;
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                LOGGER.info("Adding a new  user");
                User user = signUpCommandMapper.toUser(command);
                userService.save(user);
                responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (UserServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

    //GOOD
    @GetMapping(value = "{id}")
    public ResponseEntity<User> findUser(@PathVariable int id) {

        ResponseEntity<User> responseEntity;
        try {
            User requestedUser = userService.findById(id);
            if (requestedUser == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                org.springframework.security.core.userdetails.User principal =
                        (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                // Whether the user gets himself
                User currUser = userService.findByLogin(principal.getUsername());
                if (currUser.getId() != id) {

                    Role userRole = roleService.retrieveUserRole(SecurityContextHolder.getContext().getAuthentication());
                    Role requiredRole = roleService.retrieveUserRole(requestedUser);
                    if (roleService.compare(userRole, requiredRole) < 1) {
                        LOGGER.debug("User with role " + userRole.getName() + " tried to get the user with role " +
                                requiredRole.getName() + ". Result - FORBIDDEN");
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
                LOGGER.debug("Getting the user with id: {}", id);
                responseEntity = new ResponseEntity<>(requestedUser, HttpStatus.OK);
            }
        } catch (RoleServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

    //GOOD
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        ResponseEntity<Void> responseEntity;
        try {
            Role userRole = roleService.retrieveUserRole(SecurityContextHolder.getContext().getAuthentication());
            if (roleService.isRoleAdmin(userRole)) {
                LOGGER.debug("Deleting the user with id: {}", id);
                boolean isUserDeleted = userService.deleteById(id);
                responseEntity = (isUserDeleted) ?
                        new ResponseEntity<>(HttpStatus.OK) :
                        new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                LOGGER.debug("User with role " + userRole.getName() +
                        " tried to delete the user, but only admins can do it. Result - FORBIDDEN");
                responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (RoleServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    //GOOD
    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody @Valid UpdateUserCommand command, BindingResult bindingResult) {
        ResponseEntity<Void> responseEntity;
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                User updatedUser = userService.findById(id);
                if (updatedUser == null) {
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    User user = updateUserCommandMapper.toUser(command);
                    org.springframework.security.core.userdetails.User principal =
                            (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                    // Whether the user updates himself
                    User currUser = userService.findByLogin(principal.getUsername());
                    if (currUser.getId() != id) {

                        // if the user updates not himself, we should validate his rights
                        Role userRole = roleService.retrieveUserRole(SecurityContextHolder.getContext().getAuthentication());
                        Role requiredRole = roleService.retrieveUserRole(userService.findById(command.getId()));
                        if (roleService.compare(userRole, requiredRole) < 1) {
                            LOGGER.debug("User with role " + userRole.getName() + " tried to update the user with role " +
                                    requiredRole.getName() + ". Result - FORBIDDEN");
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    }

                    user.setId(id);
                    userService.update(user);
                    responseEntity = new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (RoleServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    //GOOD
    @GetMapping(value = "/current") // sends with Auth token
    public ResponseEntity<User> retrieveCurrentUser() {
        User user = userService.retrieveCurrentUser(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
