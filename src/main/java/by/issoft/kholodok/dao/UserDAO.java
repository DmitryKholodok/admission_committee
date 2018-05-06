package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.PageAmount;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.user.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    User findById(int id);
    Collection<User> findAll();
    User findByLogin(String login);
    void save(User user);
    boolean deleteById(int id);
    void update(User user);
    List<User> findByRoleAndPageAmount(Role role, PageAmount pageAmount);
    int getUsersCountByRole(Role role);
    List<User> findAllByPageAmount(PageAmount pageAmount);
    int getAllUsersCount();
    List<String> findEmailsByRole(Role role);
    List<User> findBirthdayPersons();
}
