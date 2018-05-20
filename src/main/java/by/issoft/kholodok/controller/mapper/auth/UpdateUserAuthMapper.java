package by.issoft.kholodok.controller.mapper.auth;

import by.issoft.kholodok.controller.command.auth.UpdateUserAuthCommand;
import by.issoft.kholodok.model.user.UserAuth;
import org.mapstruct.Mapper;

/**
 * Created by dmitrykholodok on 5/19/18
 */

@Mapper(componentModel = "spring")
public interface UpdateUserAuthMapper {

    UserAuth toUserAuth(UpdateUserAuthCommand command);

}
