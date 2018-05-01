package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.user.User;

import java.util.Collection;

public interface UserDAO {

    User findById(int id);
    Collection<User> findAll();
    User findByLogin(String login);
    void save(User user);
    boolean deleteById(int id);
    void update(User user);

}
