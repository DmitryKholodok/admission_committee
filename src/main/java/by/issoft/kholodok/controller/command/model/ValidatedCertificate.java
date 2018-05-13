package by.issoft.kholodok.controller.command.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class ValidatedCertificate {

    @Min(0)
    private int id;

    @Range(max = 100, min = 0)
    private int point;

    @NotNull
    private Date dateOfIssue;

    @Min(0)
    private int subjectId;

}
