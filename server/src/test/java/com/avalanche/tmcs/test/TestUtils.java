package com.avalanche.tmcs.test;

import com.avalanche.tmcs.auth.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Defines utility methods to do things like ensure that there's data in the database and login users
 *
 * @author ddubois
 * @since 5/15/17.
 */
public class TestUtils {
    private static String LOGIN_EMAIL       = "student@rit.edu";
    private static String LOGIN_PASSWORD    = "Password1!";

    private static RestTemplate restTemplate = new RestTemplate();

    public static String login(String urlBase) {
        User user = new User();
        user.setUsername(LOGIN_EMAIL);
        user.setPassword(LOGIN_PASSWORD);

        HttpHeaders headers = restTemplate.exchange(
                urlBase + "/user/login",
                HttpMethod.POST,
                new HttpEntity<>(user),
                new ParameterizedTypeReference<ResponseEntity<User>>() {}
                ).getHeaders();

        return "";
    }
}
