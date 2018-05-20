package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedSpecialty;
import by.issoft.kholodok.model.Specialty;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Mapper(componentModel = "spring")
public interface ValidatedSpecialtyMapper {

    Specialty toSpecialty(ValidatedSpecialty validatedSpecialty);

}
