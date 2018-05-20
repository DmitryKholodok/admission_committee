package by.issoft.kholodok.service;

import by.issoft.kholodok.controller.command.auth.UpdateUserAuthCommand;
import by.issoft.kholodok.model.user.UserAuth;

public interface UserAuthService {

    void updateUserAuth(UserAuth userAuth);
    UserAuth findById(int id);
    void updateUserAuth(UserAuth currUserAuth, UpdateUserAuthCommand command);
}
