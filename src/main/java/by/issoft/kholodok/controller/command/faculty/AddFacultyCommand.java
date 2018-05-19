package by.issoft.kholodok.controller.command.faculty;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Data
public class AddFacultyCommand {

    @NotBlank
    private String name;

}
