package by.issoft.kholodok.dao.query.impl;

import by.issoft.kholodok.dao.query.UserAuthQueryProvider;

public class HqlUserAuthQueryProvider implements UserAuthQueryProvider {

    @Override
    public String findByLogin(String login) {
        return
                "from UserAuth where login = '" + login + "'";
    }
}
