package by.issoft.kholodok.controller.mapper;

import by.issoft.kholodok.controller.command.faculty.AddFacultyCommand;
import by.issoft.kholodok.model.Faculty;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Mapper(componentModel = "spring")
public interface AddFacultyMapper {

    Faculty toFaculty(AddFacultyCommand command);

}
