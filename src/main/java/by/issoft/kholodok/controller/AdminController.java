package by.issoft.kholodok.controller;

import by.issoft.kholodok.controller.command.FindUsersByPageAmountCommand;
import by.issoft.kholodok.controller.command.SendEmailToUsersCommand;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.EmailService;
import by.issoft.kholodok.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/1/18
 */

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    // GOOD
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findAllUsers(
            @RequestParam int page,
            @RequestParam int amount) {
        ResponseEntity<List<User>> responseEntity;
        if (page <= 0 || amount <= 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            FindUsersByPageAmountCommand command = new FindUsersByPageAmountCommand(page, amount);
            List<User> userList = userService.findAllByPageAmount(command);
            responseEntity = new ResponseEntity<>(userList, HttpStatus.OK);
        }

        return responseEntity;
    }

    // GOOD
    @GetMapping(value = "/users/count")
    public ResponseEntity<Integer> getAllUsersCount() {
        int usersCount= userService.getAllUsersCount();
        return new ResponseEntity<>(usersCount, HttpStatus.OK);
    }

    @PostMapping(value = "/notify-all")
    public ResponseEntity<Void> sendMailToUsers(@Valid @RequestBody SendEmailToUsersCommand command, BindingResult bindingResult) {
        ResponseEntity<Void> responseEntity;
        try {
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(x -> LOGGER.error(x.toString()));
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                    emailService.notify(command.getFrom(), command.getTo(), command.getSubject(), command.getSubject());
                responseEntity = new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (MessagingException e) {
            LOGGER.error(e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
