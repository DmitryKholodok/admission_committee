package by.issoft.kholodok.service.impl;

import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.role.RoleEnum;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Role retrieveUserRole(final Authentication authentication) throws RoleServiceException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (!isValidRolesCount(authorities)) {
            throw new RoleServiceException("User can not have more than 1 role, " +
                    "but now " + authentication.getName() + " has " + authorities.size() + " roles");
        }
        String roleName =
                authorities
                        .stream()
                        .findFirst()
                        .get()
                        .getAuthority();
        Role role = new Role();
        role.setName(roleName);
        return role;
    }

    @Override
    public Role retrieveUserRole(final User user) throws RoleServiceException {
        Set<Role> roleSet = user.getUserAuth().getRoleSet();
        if (!isValidRolesCount(roleSet)) {
            throw new RoleServiceException("User can not have more than 1 role, " +
                    "but now " + user.getName() + " has " + roleSet.size() + " roles");
        }
        return roleSet
                .stream()
                .findFirst()
                .get();
    }

    @Override
    public boolean isRoleAdmin(final Role role) {
        return RoleEnum.ADMIN.getValue().equals(role.getName());
    }

    @Override
    public boolean isRoleEnrollee(Role role) {
        return RoleEnum.ENROLLEE.getValue().equals(role.getName());
    }

    @Override
    public int compare(Role userRole, Role comparedRole) {
        return isRoleAdmin(userRole) ?
                1 :
                Integer.compare(retrieveRolePriority(userRole), retrieveRolePriority(comparedRole));
    }

    private boolean isValidRolesCount(final Collection<?> roleCollection) {
        final int ROLE_COUNT_AT_USER = 1;
        return roleCollection.size() == ROLE_COUNT_AT_USER;
    }

    private int retrieveRolePriority(final Role role) {
        final int ROLE_PREFIX_SIZE = 5;
        return RoleEnum.valueOf(role
                .getName()
                .substring(ROLE_PREFIX_SIZE))
                .getPriority();
    }

}
