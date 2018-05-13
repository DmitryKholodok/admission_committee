package by.issoft.kholodok.controller.command.mapper;

import by.issoft.kholodok.controller.command.model.ValidatedCertificate;
import by.issoft.kholodok.model.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Mapper(componentModel = "spring")
public interface ValidatedCertificateMapper {

    @Mappings({
            @Mapping(target = "enrolleeData", ignore = true),
            @Mapping(target = "subject.id", source = "subjectId")
    })
    Certificate toCertificate(ValidatedCertificate validatedCertificate);

}
