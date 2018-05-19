package by.issoft.kholodok.controller.command;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dmitrykholodok on 5/12/18
 */

@Data
public class SignUpCommand {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    private Date dateOfBirth;

    @Email
    private String email;

    @NotBlank
    private String tel;

    @NotBlank
    private String gender;

    @NotBlank
    private String login;

    @NotBlank
    private String password;


}
