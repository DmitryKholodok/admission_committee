package by.issoft.kholodok.controller.command.enrollee.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Data
public class ValidatedSubject {

    @Min(0)
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

}
