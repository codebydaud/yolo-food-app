package com.yolo.chef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.chef.dto.CreateUserProfileRequest;
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

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Order(1)
    @Test
    @WithMockUser(authorities = "ROLE_CREATE_USER_PROFILE")
    public void testCreateUserProfileSuccess() throws Exception {
        String username = "ashharali";
        CreateUserProfileRequest request = new CreateUserProfileRequest();

        request.setFirstName("Ashhar");
        request.setLastName("Ali");
        request.setContactNumber("1234567890");
        request.setHouse("123");
        request.setStreet("Main St");
        request.setArea("Downtown");
        request.setZipCode("12345");
        request.setCity("CityName");
        request.setCountry("CountryName");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/{username}/userProfiles", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("User profile created successfully")));
    }

    @Order(2)
    @Test
    @WithMockUser(authorities = "ROLE_CREATE_USER_PROFILE")
    public void testCreateUserProfileUserNotFound() throws Exception {
        String username = "nonExistingUser";
        CreateUserProfileRequest request = new CreateUserProfileRequest();

        request.setFirstName("Ashharr");
        request.setLastName("Ali");
        request.setContactNumber("1234567890");
        request.setHouse("123");
        request.setStreet("Main St");
        request.setArea("Downtown");
        request.setZipCode("12345");
        request.setCity("CityName");
        request.setCountry("CountryName");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/{username}/userProfiles", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("User not found")));
    }

    @Order(3)
    @Test
    @WithMockUser(authorities = "ROLE_CREATE_USER_PROFILE")
    public void testCreateUserProfileWithInvalidRequest() throws Exception {
        String username = "ashharali";
        Map<String, String> request = new HashMap<>(); // Invalid request, missing required fields

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/{username}/userProfiles", username)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()) // Expect 500 Internal Server Error
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("An error occurred while creating the user profile")));
    }

}
