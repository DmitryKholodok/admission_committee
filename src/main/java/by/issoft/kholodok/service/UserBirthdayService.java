package by.issoft.kholodok.service;

import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.util.RoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/6/18
 */

@Service
public class UserBirthdayService {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Async
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void doJob() {
        try {
            String body = formBody(userService.findBirthdayPersons());
            System.out.println(body);
            String[] adminEmails = userService.findEmailsByRole(RoleProvider.getAdminRole());
            Arrays.stream(adminEmails).forEach(x -> System.out.println(x));
            emailService.notify(adminEmails, "BIRTHDAY", body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String formBody(List<User> users) {
        if (users == null) {
            return "No users have birth today!";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Users having birth today: \n");
            int counter = 1;
            users.forEach(x -> {
                sb.append(x.getName())
                        .append(" ")
                        .append(x.getSurname())
                        .append(" - ")
                        .append(x.getEmail())
                        .append("\n");
            });
            return  sb.toString();
        }
    }
}
