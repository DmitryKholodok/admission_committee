package by.issoft.kholodok.controller.command.user;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Data
public class DeleteUsersByIdsCommand {

    @Min(0)
    private int[] ids;

}
