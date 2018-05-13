package by.issoft.kholodok.controller.command;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class FindUsersByPageAmountCommand {

    @Range(min = 1)
    private int page;

    @Range(min = 10, max = 50)
    private int amount;

}
