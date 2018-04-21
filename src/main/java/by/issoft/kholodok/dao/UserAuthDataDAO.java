package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.UserAuthData;

public interface UserAuthDataDAO {

    UserAuthData findByLogin(String login);
    void update(UserAuthData userAuthData);

}
