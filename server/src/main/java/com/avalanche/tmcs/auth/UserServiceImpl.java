package com.avalanche.tmcs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        if(userDAO.findByUsername(user.getUsername()) != null) {
            throw new RegistrationException("Username already taken");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleDAO.findAll()));
        userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
