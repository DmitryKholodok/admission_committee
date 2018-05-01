package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.role.Role;

public interface RoleDAO {

    Role findRoleByName(String roleName);

}
