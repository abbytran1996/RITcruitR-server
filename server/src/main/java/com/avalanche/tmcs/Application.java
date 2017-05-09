package com.avalanche.tmcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;


/**
 * @author ddubois
 * @since 28-Feb-17.
 */
@SpringBootApplication
public class Application {
    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    private boolean shouldDeleteDatabase = false;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Autowired
            ConfigurableEnvironment environment;

            @Override
            public void run(final String... args) throws Exception {
                PropertySource<?> ps = new SimpleCommandLinePropertySource(args);
                shouldDeleteDatabase = Boolean.parseBoolean((String) ps.getProperty("rebuildDatabase"));

            }
        };
    }

    @Bean
    public Filter logFilter() {
        return new CommonsRequestLoggingFilter() {
            protected boolean shouldLog(javax.servlet.http.HttpServletRequest request) {return true;}
        };
    }
}
