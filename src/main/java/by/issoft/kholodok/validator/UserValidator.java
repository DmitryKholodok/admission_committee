package by.issoft.kholodok.validator;

import by.issoft.kholodok.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public boolean isValidUser(User user)  {
        return !(user.getName() == null ||
                user.getSurname() == null ||
                user.getUserAuthData().getLogin() == null ||
                user.getUserAuthData().getPassword() == null);
    }

}
