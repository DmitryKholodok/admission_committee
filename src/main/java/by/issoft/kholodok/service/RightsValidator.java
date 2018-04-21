package by.issoft.kholodok.service;

import by.issoft.kholodok.model.RoleEnum;
import org.springframework.stereotype.Component;

@Component
public class RightsValidator {

    public boolean isRoleEnumValid(RoleEnum userRoleEnum, RoleEnum requiredRoleEnum)  {
        return userRoleEnum == RoleEnum.ADMIN ?
                true :
                userRoleEnum.getPriority() > requiredRoleEnum.getPriority();
    }

}
