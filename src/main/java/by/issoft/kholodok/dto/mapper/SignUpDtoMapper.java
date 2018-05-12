package by.issoft.kholodok.dto.mapper;

import by.issoft.kholodok.dto.SignUpDto;
import by.issoft.kholodok.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Created by dmitrykholodok on 5/12/18
 */

@Mapper(componentModel = "spring")
public interface SignUpDtoMapper {

    @Mappings({
            @Mapping(target = "userAuth.login", source = "login"),
            @Mapping(target = "userAuth.password", source = "password")

    })
    User toUser(SignUpDto dto);

}
