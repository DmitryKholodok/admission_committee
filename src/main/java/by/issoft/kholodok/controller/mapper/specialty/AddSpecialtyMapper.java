package by.issoft.kholodok.controller.mapper.specialty;

import by.issoft.kholodok.controller.command.faculty.AddSpecialtyCommand;
import by.issoft.kholodok.model.Specialty;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Mapper(componentModel = "spring")
public interface AddSpecialtyMapper {

    Specialty toSpecialty(AddSpecialtyCommand command);

}
