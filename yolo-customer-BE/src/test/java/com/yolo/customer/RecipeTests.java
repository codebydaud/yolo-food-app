package com.yolo.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.customer.recipe.dto.RecipeRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class RecipeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @Order(1)
    @WithMockUser(username = "user", authorities = {"ROLE_VIEW_ALL_RECIPES"})
    public void testGetRecipeListSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas/recipes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("ideaId", "1")
                        .param("search", "someRecipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[0].recipe_name").value("Quinoa Salad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[0].description").value("A healthy quinoa salad with vegetables."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[0].price").value(1500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes[0].images[0]").value("https://example.com/images/quinoa_salad.jpg"));
    }


    @Test
    @Order(2)
    @WithMockUser(username = "user", authorities = {"ROLE_VIEW_ALL_RECIPES"})
    public void testGetRecipeListNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ideas/recipes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("ideaId", "9999")
                        .param("search", "nonExistentRecipe"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.recipes").isArray());
    }


    @Test
    @Order(3)
    @WithMockUser(username = "user", authorities = {"ROLE_UPDATE_RECIPE_STATUS"})
    public void testCreateRecipeSuccess() throws Exception {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://example.com/image1");

        RecipeRequest newRecipe = new RecipeRequest("New Recipe", "Description", 4, BigInteger.valueOf(25), "IDEA001", "REC123", "CHEF123", "Chef Name", imageUrls);
        String jsonRequest = objectMapper.writeValueAsString(newRecipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/ideas/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "user", authorities = {"ROLE_UPDATE_RECIPE_STATUS"})
    public void testCreateRecipeNotFound() throws Exception {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("http://example.com/image1");

        RecipeRequest newRecipe = new RecipeRequest("New Recipe", "Description", 4, BigInteger.valueOf(25), "IDEA999", "REC123", "CHEF123", "Chef Name", imageUrls);
        String jsonRequest = objectMapper.writeValueAsString(newRecipe);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/ideas/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }
}
