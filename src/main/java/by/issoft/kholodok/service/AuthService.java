package by.issoft.kholodok.service;

import by.issoft.kholodok.exception.AuthServiceException;
import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.model.User;
import by.issoft.kholodok.model.UserAuthData;
import org.springframework.security.core.Authentication;

public interface AuthService {

    boolean isUserAnonym();
    RoleEnum retrieveUserRoleEnum(Authentication authentication) throws AuthServiceException;
    RoleEnum retrieveUserRoleEnum(User user) throws AuthServiceException;
    boolean isUserAdmin(Authentication authentication) throws AuthServiceException;
    boolean isUserAdmin(RoleEnum roleEnum);
    void updateUserAuth(UserAuthData userAuthData);

}
