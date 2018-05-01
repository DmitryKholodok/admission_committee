package by.issoft.kholodok.service.impl;

import java.util.HashSet;
import java.util.Set;

import by.issoft.kholodok.dao.RoleDAO;
import by.issoft.kholodok.dao.UserAuthDAO;
import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.exception.UserServiceException;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.role.RoleEnum;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.model.user.UserAuth;
import by.issoft.kholodok.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Override
    @Transactional
    public void save(User user) throws UserServiceException {
        UserAuth userAuth = user.getUserAuth();
        if (userAuthDAO.findByLogin(userAuth.getLogin()) != null) {
            throw new UserServiceException("Current login already exists!");
        }
        userAuth.setUser(user);

        // by default, all new users have enrollee authentication data
        fillUserWithAuthData(userAuth, RoleEnum.ENROLLEE);
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

    private void fillUserWithAuthData(UserAuth userAuth, RoleEnum roleEnum) {
        final Set<Role> userRoleSet = new HashSet<>();
        final Role role = roleDAO.findRoleByName(roleEnum.getValue());
        userRoleSet.add(role);
        userAuth.setRoleSet(userRoleSet);
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
    }

}
