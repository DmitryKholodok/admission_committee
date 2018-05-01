package by.issoft.kholodok.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


/**
 * Created by dmitrykholodok on 5/1/18
 */

@Getter
@Setter
public class PageAmount {

    @Range(min = 1)
    private int page;

    @Range(min = 10, max = 50)
    private int amount;

}
