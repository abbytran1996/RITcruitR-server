package com.avalanche.tmcs.students;

import com.avalanche.tmcs.utils.ServerContext;
import com.avalanche.tmcs.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs tests against the REST endpoints defined in StudentController
 * <p>Since the controller is super simple, this seemed like the best way to test it</p>
 *
 * @author ddubois
 * @since 5/15/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentsControllerServiceTest {
    private static String URL_BASE = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String loginToken;

    @Before
    public void login() {
        ServerContext.urlBase = URL_BASE + port + "/";
        // TODO fix test
        //TestUtils.login();
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        // TODO fix test
        //assertThat(restTemplate.getForObject(URL_BASE + port + "/", String.class)).contains("Hello World");
    }
}
