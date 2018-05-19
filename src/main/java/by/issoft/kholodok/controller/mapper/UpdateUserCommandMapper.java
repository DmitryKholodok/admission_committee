package by.issoft.kholodok.controller.mapper;

import by.issoft.kholodok.controller.command.UpdateUserCommand;
import by.issoft.kholodok.model.user.User;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/13/18
 */

@Mapper(componentModel = "spring")
public interface UpdateUserCommandMapper {

    User toUser(UpdateUserCommand dto);

}
