package by.issoft.kholodok.auth.service;

import by.issoft.kholodok.dao.UserAuthDataDAO;
import by.issoft.kholodok.model.Role;
import by.issoft.kholodok.model.UserAuthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthDataDAO userAuthDataDAO;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        final UserAuthData userAuthData = userAuthDataDAO.findByLogin(login);
        if (userAuthData == null) {
            throw new UsernameNotFoundException("Login " + login + " was not found!");
        }

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        Set<Role> userRoleSet = userAuthData.getRoleSet();
        for (Role role : userRoleSet) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(userAuthData.getLogin(), userAuthData.getPassword(), grantedAuthoritySet);
    }


}
