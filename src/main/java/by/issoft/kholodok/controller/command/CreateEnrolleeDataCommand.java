package by.issoft.kholodok.controller.command;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class CreateEnrolleeDataCommand {

    @Min(1)
    private int userId;

}
