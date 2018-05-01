package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.user.UserAuth;

public interface UserAuthDAO {

    UserAuth findByLogin(String login);
    void update(UserAuth userAuthData);

}
