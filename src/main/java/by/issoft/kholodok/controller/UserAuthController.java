package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.UpdateUserAuthCommand;
import by.issoft.kholodok.controller.mapper.UpdateUserAuthMapper;
import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.service.RoleService;
import by.issoft.kholodok.service.UserAuthService;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.model.user.UserAuth;
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
@RequestMapping(value = "/api/auth")
public class UserAuthController {

    private static final Logger LOGGER = LogManager.getLogger(UserAuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UpdateUserAuthMapper userAuthMapper;

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateUserAuth(
            @PathVariable int id,
            @RequestBody @Valid UpdateUserAuthCommand command,
            BindingResult bindingResult) {
        ResponseEntity<Void> responseEntity;
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                User updatedUser = userService.findById(id);
                if (updatedUser != null) {
                    org.springframework.security.core.userdetails.User principal =
                            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    User currUser = userService.findByLogin(principal.getUsername());
                    if (currUser.getId() != id) {

                        // if the user updates not himself, we should validate his rights
                        Role userRole = roleService.retrieveUserRole(SecurityContextHolder.getContext().getAuthentication());
                        Role requiredRole = roleService.retrieveUserRole(updatedUser);
                        if (roleService.compare(userRole, requiredRole) < 1) {
                            LOGGER.debug("User with role " + userRole.getName() + " tried to update the userAuth data of " +
                                    "user who has role " + requiredRole.getName() + ". Result - FORBIDDEN");
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    }
                    UserAuth userAuth = userAuthMapper.toUserAuth(command);
                    userAuthService.updateUserAuth(userAuth);
                    responseEntity = new ResponseEntity<>(HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (RoleServiceException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/login/exists", method = RequestMethod.HEAD)
    public ResponseEntity<Void> isUserLoginExists(@RequestParam String login) {
        return (userService.findByLogin(login) != null) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
