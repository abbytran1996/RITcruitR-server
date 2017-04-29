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
public class LoginController {
    private SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user) {
        if(securityService.login(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok().build();
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
