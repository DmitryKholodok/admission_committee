package by.issoft.kholodok.controller.command;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Data
public class UpdateUserAuthCommand {

    @NotNull
    private Integer id;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
