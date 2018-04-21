package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.Role;

public interface RoleDAO {

    Role findRoleByName(String roleName);

}
