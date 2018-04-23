package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.User;

import java.util.List;

public interface UserDAO {

    User findById(int id);
    List<User> findAll();
    User findByLogin(String login);
    void save(User user);
    boolean deleteById(int id);
    void update(User user);

}
