package by.issoft.kholodok.controller;

import by.issoft.kholodok.exception.AuthServiceException;
import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.model.User;
import by.issoft.kholodok.model.UserAuthData;
import by.issoft.kholodok.service.AuthService;
import by.issoft.kholodok.service.RightsValidator;
import by.issoft.kholodok.service.UserService;
import by.issoft.kholodok.validator.UserAuthValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthValidator userAuthValidator;

    @Autowired
    private AuthService authService;

    @Autowired
    private RightsValidator rightsValidator;

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateUserAuth(@PathVariable int id, UserAuthData userAuthData) {

        ResponseEntity<Void> responseEntity;
        try {
            if (userAuthValidator.isUserAuthValid(userAuthData)) {
                User updatedUser = userService.findById(id);
                if (updatedUser != null) {
                    org.springframework.security.core.userdetails.User principal =
                            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    User currUser = userService.findByLogin(principal.getUsername());

                    if (currUser.getId() != id) {
                        // if the user updates not himself, we should validate his rights
                        RoleEnum userRoleEnum = authService.retrieveUserRoleEnum(SecurityContextHolder.getContext().getAuthentication());
                        RoleEnum requiredRoleEnum = authService.retrieveUserRoleEnum(updatedUser);

                        if (!rightsValidator.isRoleEnumValid(userRoleEnum, requiredRoleEnum)) {
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    }

                    authService.updateUserAuth(userAuthData);
                    responseEntity = new ResponseEntity<>(HttpStatus.OK);

                } else {
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (AuthServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }

        return responseEntity;
    }

}
