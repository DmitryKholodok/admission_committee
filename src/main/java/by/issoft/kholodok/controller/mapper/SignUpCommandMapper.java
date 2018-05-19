package by.issoft.kholodok.controller.mapper;

import by.issoft.kholodok.controller.command.SignUpCommand;
import by.issoft.kholodok.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/12/18
 */

@Mapper(componentModel = "spring")
public interface SignUpCommandMapper {

    @Mappings({
            @Mapping(target = "userAuth.login", source = "login"),
            @Mapping(target = "userAuth.password", source = "password")

    })
    User toUser(SignUpCommand dto);

}
