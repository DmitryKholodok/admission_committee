package by.issoft.kholodok.controller.command.enrollee.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class ValidatedCertificate {

    @Min(0)
    @NotNull
    private Integer id;

    @Range(max = 100, min = 0)
    @NotNull
    private Integer point;

    @NotNull
    private Date dateOfIssue;

    @Valid
    private ValidatedSubject subject;

}
