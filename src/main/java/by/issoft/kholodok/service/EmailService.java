package by.issoft.kholodok.service;

import by.issoft.kholodok.common.EmailSender;
import by.issoft.kholodok.controller.command.SendEmailToUsersCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by dmitrykholodok on 5/6/18
 */

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    public void notify(String from, String[] to, String subject, String body) throws MessagingException {
        emailSender.send(from, to, subject, body);
    }

}
