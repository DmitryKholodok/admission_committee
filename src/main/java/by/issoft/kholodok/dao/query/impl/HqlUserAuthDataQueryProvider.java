package by.issoft.kholodok.dao.query.impl;

import by.issoft.kholodok.dao.query.UserAuthDataQueryProvider;

public class HqlUserAuthDataQueryProvider implements UserAuthDataQueryProvider {

    @Override
    public String findByLogin(String login) {
        return
                "from UserAuthData where login = '" + login + "'";
    }
}
