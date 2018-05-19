package by.issoft.kholodok.controller.command.faculty;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Data
public class AddSpecialtyCommand {

    @NotBlank
    private String name;

    @NotNull
    private Integer chargeablePlaceCount;

    @NotNull
    private Integer budgetaryPlaceCount;

}
