package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.CreateEnrolleeDataCommand;
import by.issoft.kholodok.model.EnrolleeData;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Mapper(componentModel = "spring", uses = {
        ValidatedCertificateMapper.class,
        ValidatedBasicCertificateMapper.class,
        ValidatedSpecialtyEnrolleeMapper.class
})
public interface AddEnrolleeDataMapper {

    EnrolleeData toEnrolleeData(CreateEnrolleeDataCommand command);

}
