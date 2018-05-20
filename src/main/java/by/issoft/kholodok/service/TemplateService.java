package by.issoft.kholodok.service;

import by.issoft.kholodok.controller.command.mail.SendEmailToUsersCommand;
import by.issoft.kholodok.controller.command.mail.TemplateEnum;
import by.issoft.kholodok.model.Certificate;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.model.user.GenderEnum;
import by.issoft.kholodok.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dmitrykholodok on 5/15/18
 */

@Service
public class TemplateService {

    private static final Logger LOGGER = LogManager.getLogger(TemplateService.class);

    private static final String ENTER = "<br>";

    @Autowired
    private UserService userService;

    @Autowired
    private EnrolleeDataService enrolleeDataService;

    public List<SendEmailToUsersCommand> retrieveHandledCommandList(SendEmailToUsersCommand command) {
        List<SendEmailToUsersCommand> commands = new ArrayList<>();
        try {
            TemplateEnum templateEnum = TemplateEnum.valueOf(command.getTemplate());
            switch (templateEnum) {

                case OPERATOR_NOTIFICATION: {
                    Arrays.stream(command.getTo()).forEach(email -> {
                        User user = userService.findByEmail(email);
                        if (user != null) {
                            String body;
                            if (user.getGenderEnum().equals(GenderEnum.M)) {
                                body = "Уважаемый " + user.getName() + " " + user.getSurname() + "!" + ENTER;
                            } else {
                                body = "Уважаемая " + user.getName() + " " + user.getSurname() + "!" + ENTER;
                            }
                            body += command.getBody() + ENTER;
                            body += "Kind Regards, " + ENTER +
                                    "Your Bsuir-БГУИР-2018 (С).";
                            SendEmailToUsersCommand resCommand = new SendEmailToUsersCommand();
                            resCommand.setFrom(command.getFrom());
                            resCommand.setSubject(command.getSubject());
                            resCommand.setBody(body);
                            String[] to = new String[1];
                            to[0] = email;
                            resCommand.setTo(to);
                            commands.add(resCommand);
                        } else {
                            LOGGER.debug("User with email " + email + " was not found!");
                        }
                    });
                    break;
                }

                case ENROLLEE_ENLISTMENT: {
                    Arrays.stream(command.getTo()).forEach(email -> {
                        User user = userService.findByEmail(email);
                        if (user != null) {
                            String bodyOperator;
                            EnrolleeData enrolleeData = enrolleeDataService.findById(user.getId());
                            if (enrolleeData != null) {
                                if (user.getGenderEnum().equals(GenderEnum.M)) {
                                    bodyOperator = "Уважаемый " + user.getName() + " " + user.getSurname() + "!" + ENTER;
                                } else {
                                    bodyOperator = "Уважаемая " + user.getName() + " " + user.getSurname() + "!" + ENTER;
                                }
                                bodyOperator += command.getBody() + ENTER;
                                bodyOperator += "Ваш general point: " + sumGeneralPoint(enrolleeData) + "." + ENTER;
                                bodyOperator += "Ваша specialty: " + getSpecialty(enrolleeData) + "." + ENTER;
                                bodyOperator += "Kind Regards, " + ENTER +
                                        "Your Bsuir-БГУИР-2018 (С).";
                                SendEmailToUsersCommand resCommand = new SendEmailToUsersCommand();
                                resCommand.setFrom(command.getFrom());
                                resCommand.setSubject(command.getSubject());
                                resCommand.setBody(bodyOperator);
                                String[] to = new String[1];
                                to[0] = email;
                                resCommand.setTo(to);
                                commands.add(resCommand);
                            } else {
                                LOGGER.debug("User with email " + email + " does not have any enrollee data!");
                            }
                        } else {
                            LOGGER.debug("User with email " + email + " was not found!");
                        }
                    });
                    break;
                }
            }


        } catch (Exception e) {
            LOGGER.error(e);
        }
        return commands;
    }

    private String getSpecialty(EnrolleeData enrolleeData) {
        return enrolleeData.getSpecialtyEnrollee().getSpecialty().getName();
    }

    private String sumGeneralPoint(EnrolleeData enrolleeData) {
        int sum = 0;
        Iterator<Certificate> iterator = enrolleeData.getCertificates().iterator();
        while(iterator.hasNext()) {
            Certificate certificate = iterator.next();
            sum += certificate.getPoint();
        }
        sum += enrolleeData.getBasicCertificate().getPoint();
        return String.valueOf(sum);
    }
}
