package by.issoft.kholodok.controller.command.mapper;

import by.issoft.kholodok.controller.command.UpdateEnrolleeDataCommand;
import by.issoft.kholodok.model.EnrolleeData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Mapper(componentModel = "spring", uses = ValidatedCertificateMapper.class)
public interface UpdateEnrolleeDataMapper {

    @Mappings({
            @Mapping(source = "bcId", target = "basicCertificate.id"),
            @Mapping(source = "bcPoint", target = "basicCertificate.point"),
            @Mapping(source = "bcDateOfIssue", target = "basicCertificate.dateOfIssue"),
            @Mapping(source = "specialtyId", target = "specialtyEnrollee.specialty.id"),
    })
    EnrolleeData toEnrolleeData(UpdateEnrolleeDataCommand command);

}
