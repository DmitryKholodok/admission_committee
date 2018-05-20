package by.issoft.kholodok.controller.command.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedBasicCertificate;
import by.issoft.kholodok.controller.command.enrollee.model.ValidatedCertificate;
import by.issoft.kholodok.controller.command.enrollee.model.ValidatedSpecialtyEnrollee;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Data
public class CreateEnrolleeDataCommand {

    @Min(1)
    @NotNull
    private Integer id;

    @Valid
    private ValidatedBasicCertificate basicCertificate;

    @Valid
    private ValidatedSpecialtyEnrollee specialtyEnrollee;

    @Valid
    private Set<ValidatedCertificate> certificates;

}
