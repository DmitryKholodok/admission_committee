package by.issoft.kholodok.service;

import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.user.User;
import org.springframework.security.core.Authentication;

import java.util.Comparator;

public interface RoleService extends Comparator<Role> {

    Role retrieveUserRole(final Authentication authentication) throws RoleServiceException;
    Role retrieveUserRole(final User user) throws RoleServiceException;
    boolean isRoleAdmin(final Role role);
    boolean isRoleEnrollee(final Role role);

}
