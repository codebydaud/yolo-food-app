package com.yolo.chef;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    @WithMockUser
    public void testCheckUserProfileExistsSuccess() throws Exception {
        String username = "ashharali";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$._user_profile_completed", Matchers.is(true)));
    }

    @Order(2)
    @Test
    @WithMockUser
    public void testCheckUserProfileDoesNotExist() throws Exception {
        String username = "nonExistingUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$._user_profile_completed", Matchers.is(false)));
    }




    @Order(3)
    @Test
    @WithMockUser
    public void testCheckUserProfileWithInvalidUsername() throws Exception {
        String username = ""; // or null, depending on how your application handles it

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
