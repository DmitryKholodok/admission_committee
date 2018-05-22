package by.issoft.kholodok.service.impl;

import by.issoft.kholodok.controller.command.auth.UpdateUserAuthCommand;
import by.issoft.kholodok.dao.UserAuthDAO;
import by.issoft.kholodok.model.user.UserAuth;
import by.issoft.kholodok.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Override
    public void updateUserAuth(UserAuth userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        userAuthDAO.update(userAuth);
    }

    @Override
    public UserAuth findById(int id) {
        return userAuthDAO.findById(id);
    }

    @Override
    public void updateUserAuth(UserAuth currUserAuth, UpdateUserAuthCommand command) {
        currUserAuth.setPassword(passwordEncoder.encode(command.getPassword()));
        //currUserAuth.setLogin(command.getLogin());
        userAuthDAO.update(currUserAuth);
    }

}
