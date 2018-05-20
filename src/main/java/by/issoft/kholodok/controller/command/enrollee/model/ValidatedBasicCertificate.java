package by.issoft.kholodok.controller.command.enrollee.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Data
public class ValidatedBasicCertificate {

    @Min(0)
    @NotNull
    private Integer id;

    @Min(0)
    @NotNull
    private Integer bcId;

    @Range(max = 100, min = 0)
    private Integer point;

    @NotNull
    private Date dateOfIssue;

}
