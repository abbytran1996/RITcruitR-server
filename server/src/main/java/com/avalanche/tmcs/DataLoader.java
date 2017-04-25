package com.avalanche.tmcs;

import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.RoleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Gives the database its initial data
 */
@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

    private RoleDAO roleDAO;

    @Autowired
    public DataLoader(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public void run(ApplicationArguments args) {
        if(roleDAO.findByName("student") == null) {
            roleDAO.save(new Role("student"));
        }

        if(roleDAO.findByName("recruiter") == null) {
            roleDAO.save(new Role("recruiter"));
        }

        if(roleDAO.findByName("admin") == null) {
            roleDAO.save(new Role("admin"));
        }

        LOG.info("Added Role definitions");
    }
}