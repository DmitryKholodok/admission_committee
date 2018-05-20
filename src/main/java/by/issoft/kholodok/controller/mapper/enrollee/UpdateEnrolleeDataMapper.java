package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.UpdateEnrolleeDataCommand;
import by.issoft.kholodok.model.EnrolleeData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Mapper(componentModel = "spring", uses = {
        ValidatedCertificateMapper.class,
        ValidatedBasicCertificateMapper.class,
        ValidatedSpecialtyEnrolleeMapper.class
})
public interface UpdateEnrolleeDataMapper {

    EnrolleeData toEnrolleeData(UpdateEnrolleeDataCommand command);

}
