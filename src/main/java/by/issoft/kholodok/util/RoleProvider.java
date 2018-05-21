package by.issoft.kholodok.util;

import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.role.RoleEnum;

/**
 * Created by dmitrykholodok on 5/6/18
 */
public class RoleProvider {

    public static Role getAdminRole() {
        return getRoleImpl(RoleEnum.ADMIN);
    }

    public static Role getEnrolleeRole() {
        return getRoleImpl(RoleEnum.ENROLLEE);
    }

    private static Role getRoleImpl(RoleEnum roleEnum) {
        Role role = new Role();
        role.setName(roleEnum.getValue());
        return role;
    }

}
