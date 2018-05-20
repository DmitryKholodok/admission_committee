package by.issoft.kholodok.controller.mapper.enrollee;

import by.issoft.kholodok.controller.command.enrollee.model.ValidatedSubject;
import by.issoft.kholodok.model.Subject;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/20/18
 */

@Mapper(componentModel = "spring")
public interface ValidatedSubjectMapper {

    Subject toSubject(ValidatedSubject validatedSubject);

}
