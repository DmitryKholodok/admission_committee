package by.issoft.kholodok.dao.query.impl;

import by.issoft.kholodok.dao.query.UserQueryProvider;
import by.issoft.kholodok.model.PageAmount;
import by.issoft.kholodok.model.role.Role;

public class HqlUserQueryProvider implements UserQueryProvider {

    public String findByLogin(String login) {
        return "from User as user " +
                "where user.userAuth.login = '" + login + "'";
    }

    @Override
    public String getAllUsersCount() {
        return "select count(*) from User";
    }


}
