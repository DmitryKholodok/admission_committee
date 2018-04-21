package by.issoft.kholodok.dao.query.impl;

import by.issoft.kholodok.dao.query.RoleQueryProvider;

public class HqlRoleQueryProvider implements RoleQueryProvider {

    @Override
    public String findRoleByName(String name) {
        return
                "from Role where name = '" + name + "'";
    }
}
