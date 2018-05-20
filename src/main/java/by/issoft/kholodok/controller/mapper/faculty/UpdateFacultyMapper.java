package by.issoft.kholodok.controller.mapper.faculty;

import by.issoft.kholodok.controller.command.faculty.UpdateFacultyCommand;
import by.issoft.kholodok.model.Faculty;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Mapper(componentModel = "spring")
public interface UpdateFacultyMapper {

    Faculty toFaculty(UpdateFacultyCommand command);

}
