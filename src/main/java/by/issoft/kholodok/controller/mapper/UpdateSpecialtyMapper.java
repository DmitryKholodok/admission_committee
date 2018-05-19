package by.issoft.kholodok.controller.mapper;

import by.issoft.kholodok.controller.command.faculty.UpdateSpecialtyCommand;
import by.issoft.kholodok.model.Specialty;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Mapper(componentModel = "spring")
public interface UpdateSpecialtyMapper {

    Specialty toSpecialty(UpdateSpecialtyCommand command);

}
