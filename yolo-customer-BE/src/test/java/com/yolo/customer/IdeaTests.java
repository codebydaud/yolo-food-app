package com.yolo.customer;

import com.yolo.customer.idea.IdeaRepository;
import com.yolo.customer.idea.IdeaService;
import com.yolo.customer.user.UserRepository;
import org.junit.jupiter.api.*;
import org.hamcrest.Matchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;
import util.SecurityTestUtil;

import java.util.Map;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(properties = {"api.security.max_limit=3","api.security.title_length=64","api.security.description_length=128"})
class IdeaTests {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private IdeaService ideaService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IdeaRepository ideaRepository;

    @BeforeEach
    public void setUp() {
        SecurityTestUtil.setJwtAuthenticationToken("ahmad",
                Set.of("ROLE_UPDATE_IDEA_STATUS"),
                Map.of("preferred_username", "admin")
        );
    }

    @Order(1)
    @Test
    public void testSubmitIdeaToVendor_Success() throws Exception {
        String ideaRequestPayload = "{"
                + "\"status\": \"draft\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/ideas/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ideaRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Idea submitted and status updated successfully."));
    }

    @Order(2)
    @Test
    public void testSubmitIdeaToVendor_Failure_InvalidStatus() throws Exception {
        String ideaRequestPayload = "{"
                + "\"status\": \"\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/ideas/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ideaRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Status cannot be empty."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An error occurred"));

    }

    @Order(3)
    @Test
    public void testSubmitIdeaToVendor_Failure_IdeaNotFound() throws Exception {
        String ideaRequestPayload = "{"
                + "\"status\": \"Draft\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/ideas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ideaRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Ideas not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An error occurred"));
    }

    @Order(4)
    @Test
    public void testSubmitIdeaToVendor_Failure_Unauthorized() throws Exception {
        String ideaRequestPayload = "{"
                + "\"status\": \"Draft\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/users/ideas/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ideaRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Unauthorized to update idea."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An error occurred"));

    }

    @Order(5)
    @Test
    @WithMockUser(username = "admin", authorities = {"VIEW_ALL_IDEAS"})
    public void testGetIdeas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas")
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.ideas.content.length()").value(1));
    }

    @Order(6)
    @Test
    @WithMockUser(username = "admin")
    public void testGetIdeas_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas")
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "createdAt,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

//    @Order(7)
//    @Test
//    public void testGetIdeas_NotAuthenticated() throws Exception {
//        SecurityTestUtil.clearAuthentication();
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas")
//                        .param("page", "0")
//                        .param("size", "2")
//                        .param("sort", "createdAt,desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }

    @Order(8)
    @Test
    @WithMockUser(username = "admin", authorities = {"VIEW_ALL_IDEAS"})
    public void testGetIdeas_ServiceException() throws Exception {
        SecurityTestUtil.setJwtAuthenticationToken("viewer",
                Set.of("VIEW_ALL_IDEAS"),
                Map.of("preferred_username", "viewer")
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "createdAt,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unexpected error"));
    }
}
