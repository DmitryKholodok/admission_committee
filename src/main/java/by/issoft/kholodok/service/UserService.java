package by.issoft.kholodok.service;

import by.issoft.kholodok.controller.command.FindUsersByPageAmountCommand;
import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    void save(User user) throws UserServiceException;
    boolean isUserExists(User user);
    User findById(int id);
    boolean deleteById(int id);
    void update(User user);
    User findByLogin(String login);
    List<User> findByRoleAndPageAmount(Role role, FindUsersByPageAmountCommand pageAmount);
    int getUsersCountByRole(Role role);
    List<User> findAllByPageAmount(FindUsersByPageAmountCommand pageAmount);
    int getAllUsersCount();
    String[] findEmailsByRole(Role role);
    List<User> findBirthdayPersons();
    User findByEmail(String email);
    User retrieveCurrentUser(Authentication authentication);
}
