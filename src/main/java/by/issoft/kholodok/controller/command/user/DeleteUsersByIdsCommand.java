package by.issoft.kholodok.controller.command.user;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Data
public class DeleteUsersByIdsCommand {

    @NotNull
    private int[] ids;

}
