package by.issoft.kholodok.service.impl;

import java.util.HashSet;
import java.util.Set;

import by.issoft.kholodok.dao.RoleDAO;
import by.issoft.kholodok.dao.UserAuthDataDAO;
import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.Role;
import by.issoft.kholodok.model.RoleEnum;
import by.issoft.kholodok.model.User;
import by.issoft.kholodok.model.UserAuthData;
import by.issoft.kholodok.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAuthDataDAO userAuthDataDAO;

    @Override
    public void save(User user) throws UserServiceException {
        UserAuthData userAuthData = user.getUserAuthData();
        if (userAuthDataDAO.findByLogin(userAuthData.getLogin()) != null) {
            throw new UserServiceException("Current login already exists!");
        }
        userAuthData.setUser(user);

        // by default, all new users have enrollee authentication data
        fillUserWithAuthData(userAuthData, RoleEnum.ENROLLEE);
        userDAO.save(user);
    }

    @Override
    public boolean isUserExists(User user) {
        return false;
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return userDAO.deleteById(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findByLogin(String login) {
        return userDAO.findByLogin(login);
    }

    private void fillUserWithAuthData(UserAuthData userAuthData, RoleEnum roleEnum) {
        final Set<Role> userRoleSet = new HashSet<>();
        final Role role = roleDAO.findRoleByName(roleEnum.getValue());
        userRoleSet.add(role);
        userAuthData.setRoleSet(userRoleSet);
        userAuthData.setPassword(passwordEncoder.encode(userAuthData.getPassword()));
    }

}
