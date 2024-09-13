package com.yolo.customer;

import com.yolo.customer.userProfile.UserProfileRequestDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.SecurityTestUtil;

import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        SecurityTestUtil.setJwtAuthenticationToken("admin",
                Set.of("UPDATE_PROFILE", "VIEW_PROFILE", "CREATE_USER","CREATE_ACCOUNT"),
                Map.of("preferred_username", "admin")
        );
    }

//    @Test
//    @Order(1)
//    @WithMockUser(username = "admin", authorities = {"ROLE_CREATE_USER","ROLE_VIEW_PROFILE", "ROLE_CREATE_ACCOUNT"})
//    public void testCreateUserProfileSuccess() throws Exception {
//        String userJson = "{\"username\":\"testUser\",\"email\":\"testuser@example.com\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isCreated());
//
//        String userProfileJson = "{\"first_name\":\"John\",\"last_name\":\"Doe\",\"contact_number\":\"1234567890\",\"house\":\"12\",\"street\":\"Main Street\",\"area\":\"Downtown\",\"zip_code\":\"12345\",\"city\":\"Metropolis\",\"country\":\"Country\",\"currency_code\":\"USD\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/profiles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userProfileJson))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("User profile created successfully."));
//    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = {"ROLE_UPDATE_PROFILE"})
    public void testUpdateUserProfileSuccess() throws Exception {
        String userProfileUpdateJson = "{\"city\":\"Gotham\"}";
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userProfileUpdateJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User profile updated successfully."));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = {"ROLE_VIEW_PROFILE"})
    public void testUserProfileExistSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profiles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = {"ROLE_VIEW_PROFILE"})
    public void testViewUserProfileSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
