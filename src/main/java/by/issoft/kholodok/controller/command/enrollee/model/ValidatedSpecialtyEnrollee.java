package by.issoft.kholodok.controller.command.enrollee.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Data
public class ValidatedSpecialtyEnrollee {

    @Min(0)
    @NotNull

    private Integer id;

    @Valid
    private ValidatedSpecialty specialty;

    @NotNull
    private Boolean chargeable;

}
