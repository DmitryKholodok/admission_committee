package by.issoft.kholodok.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dmitrykholodok on 5/12/18
 */

@Data
public class SignUpDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotNull
    private Date dateOfBirth;

    @Email
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String password;


}
