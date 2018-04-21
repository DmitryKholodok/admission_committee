package by.issoft.kholodok.dao.query.impl;

import by.issoft.kholodok.dao.query.UserQueryProvider;

public class HqlUserQueryProvider implements UserQueryProvider {

    public String findByLogin(String login) {
        return "from User as user " +
                "where user.userAuthData.login = '" + login + "'";
    }
}
