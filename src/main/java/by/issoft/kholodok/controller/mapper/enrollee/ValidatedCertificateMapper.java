package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedCertificate;
import by.issoft.kholodok.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Mapper(componentModel = "spring", uses = ValidatedSubjectMapper.class)
public interface ValidatedCertificateMapper {

    Certificate toCertificate(ValidatedCertificate validatedCertificate);

}
