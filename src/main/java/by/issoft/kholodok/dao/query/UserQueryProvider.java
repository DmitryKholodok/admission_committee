package by.issoft.kholodok.dao.query;

import by.issoft.kholodok.model.role.Role;

public interface UserQueryProvider {

    String findByLogin(String login);
    String getAllUsersCount();

}
