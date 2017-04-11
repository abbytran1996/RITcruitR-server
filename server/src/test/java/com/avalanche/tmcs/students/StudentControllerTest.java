package com.avalanche.tmcs.students;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author David Dubois
 * @since 09-Apr-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .content("{\"firstName\":\"John\",\"lastName\":\"Smith\",\"email\":\"jsmith@gmail.com\",\"password\":\"supersecret\",\"graduationDate\":\"2018-05-20\",\"school\":\"Westbrook\"}")
                                              .header("Content-Type", "application/json"))
                .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/students/0"));
    }
}
