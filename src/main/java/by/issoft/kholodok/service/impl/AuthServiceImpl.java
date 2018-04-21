package by.issoft.kholodok.service.impl;

import by.issoft.kholodok.dao.UserAuthDataDAO;
import by.issoft.kholodok.exception.AuthServiceException;
import by.issoft.kholodok.model.Role;
import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.model.User;
import by.issoft.kholodok.model.UserAuthData;
import by.issoft.kholodok.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserAuthDataDAO userAuthDataDAO;

    @Override
    public boolean isUserAnonym() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleGrantedAuthority anonymAuth = new SimpleGrantedAuthority(RoleEnum.ANONYMOUS.getValue());
        return authentication.getAuthorities().contains(anonymAuth);
    }

    @Override
    public RoleEnum retrieveUserRoleEnum(final Authentication authentication) throws AuthServiceException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (!isValidRoleCollectionSize(authorities)) {
            throw new AuthServiceException("User can not have more than 1 role, " +
                    "but now " + authentication.getName() + " has " + authorities.size() + " roles");
        }
        return retrieveRoleEnum(authorities.stream().map(x ->  x.getAuthority()));

    }

    @Override
    public RoleEnum retrieveUserRoleEnum(final User user) throws AuthServiceException {
        Set<Role> roleSet = user.getUserAuthData().getRoleSet();
        if (!isValidRoleCollectionSize(roleSet)) {
            throw new AuthServiceException("User can not have more than 1 role, " +
                    "but now " + user.getName() + " has " + user.getUserAuthData().getRoleSet().size() + " roles");
        }
        return retrieveRoleEnum(roleSet.stream().map(x -> x.getName()));
    }

    @Override
    public boolean isUserAdmin(final Authentication authentication) throws AuthServiceException {
        return retrieveUserRoleEnum(authentication) == RoleEnum.ADMIN;
    }

    @Override
    public boolean isUserAdmin(final RoleEnum roleEnum) {
        return roleEnum == RoleEnum.ADMIN;
    }

    @Override
    public void updateUserAuth(UserAuthData userAuthData) {
        userAuthDataDAO.update(userAuthData);
    }

    private boolean isValidRoleCollectionSize(final Collection<?> roleCollection) {
        final int ROLE_COUNT_AT_USER = 1;
        return roleCollection.size() >= ROLE_COUNT_AT_USER;
    }

    private RoleEnum retrieveRoleEnum(final Stream<String> roleStream) {
        final int ROLE_PREFIX_SIZE = 5;
        return RoleEnum.valueOf(roleStream
                .findFirst()
                .get()
                .substring(ROLE_PREFIX_SIZE));
    }

}
