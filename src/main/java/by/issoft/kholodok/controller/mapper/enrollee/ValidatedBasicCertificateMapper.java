package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedBasicCertificate;
import by.issoft.kholodok.model.BasicCertificate;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Mapper(componentModel = "spring")
public interface ValidatedBasicCertificateMapper {

    BasicCertificate toBasicCertificate(ValidatedBasicCertificate validatedBasicCertificate);

}
