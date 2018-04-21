package by.issoft.kholodok.validator;

import by.issoft.kholodok.model.UserAuthData;
import org.springframework.stereotype.Component;

@Component
public class UserAuthValidator {

    public boolean isUserAuthValid(UserAuthData userAuthData) {
        return (userAuthData.getLogin() != null && userAuthData.getPassword() != null);
    }

}
