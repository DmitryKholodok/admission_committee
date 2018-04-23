package by.issoft.kholodok.service;

import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.User;

import java.util.List;

public interface UserService {

    void save(User user) throws UserServiceException;
    boolean isUserExists(User user);
    User findById(int id);
    boolean deleteById(int id);
    void update(User user);
    User findByLogin(String login);
    List<User> findAll();
}
