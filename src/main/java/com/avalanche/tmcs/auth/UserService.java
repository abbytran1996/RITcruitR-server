package com.avalanche.tmcs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Service
public class UserService {
    private UserDAO userDAO;

    private RoleDAO roleDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User save(User user, Role.RoleName roleName) {
        if(userDAO.findByUsername(user.getUsername()) != null) {
            throw new RegistrationException("Username already taken");
        }

        Role studentRole = roleDAO.findByName(roleName.name().toLowerCase());
        user.addRole(studentRole);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}