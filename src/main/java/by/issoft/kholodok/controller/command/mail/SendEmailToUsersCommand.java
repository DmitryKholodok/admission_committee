package by.issoft.kholodok.controller.command.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/6/18
 */

@Getter
@Setter
@NoArgsConstructor
public class SendEmailToUsersCommand {

    @NotNull
    private String[] to;

    @NotNull
    private String from;

    @NotNull
    private String subject;

    @NotNull
    private String body;

    private String template;

}
