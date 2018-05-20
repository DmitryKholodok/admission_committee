package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedSpecialtyEnrollee;
import by.issoft.kholodok.model.SpecialtyEnrollee;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Mapper(componentModel = "spring", uses = ValidatedSpecialtyMapper.class)
public interface ValidatedSpecialtyEnrolleeMapper {

    SpecialtyEnrollee toSpecialtyEnrollee(ValidatedSpecialtyEnrollee validatedSpecialtyEnrollee);

}
