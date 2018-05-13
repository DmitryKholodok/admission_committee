package by.issoft.kholodok.controller.command;

import by.issoft.kholodok.controller.command.model.ValidatedCertificate;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class UpdateEnrolleeDataCommand {

    @Min(0)
    private int bcId;

    @Range(min = 0, max = 100)
    private int bcPoint;

    @NotNull
    private Date bcDateOfIssue;

    @Min(1)
    private int specialtyId;

    @Valid
    private Set<ValidatedCertificate> certificates;

}
