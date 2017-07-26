package com.avalanche.tmcs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Defines and implements an API for logging in and out
 *
 * @author David Dubois
 * @since 18-Apr-17.
 */
@RestController
@RequestMapping("/user")
public class UsersController {
    private SecurityService securityService;

    private UserDAO userDAO;

    @Autowired
<<<<<<< HEAD:server/src/main/java/com/avalanche/tmcs/auth/UsersController.java
    public UsersController(SecurityService securityService, UserDAO userDAO, UserService userService) {
=======
    public LoginController(SecurityService securityService, UserDAO userDAO) {
>>>>>>> 10a5dca3c5e4d7abe9538abfa32126572a6be29c:server/src/main/java/com/avalanche/tmcs/auth/LoginController.java
        this.securityService = securityService;
        this.userDAO = userDAO;
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user) {
        if(securityService.login(user.getUsername(), user.getPassword())) {
            user = userDAO.findByUsername(user.getUsername());
            user.setPassword("");
            return ResponseEntity.ok(user);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
