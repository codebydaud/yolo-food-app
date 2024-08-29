package com.yolo.chef;

import io.github.cdimascio.dotenv.Dotenv;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = { "ENV_VARIABLE=DB_TEST_URL", "ENV_VARIABLE=DB_TEST_USERNAME", "ENV_VARIABLE=DB_TEST_PASSWORD"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RecipeControllerTests {

    @BeforeAll
    static void initAll() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_TEST_URL", dotenv.get("DB_TEST_URL"));
        System.setProperty("DB_TEST_USERNAME", dotenv.get("DB_TEST_USERNAME"));
        System.setProperty("DB_TEST_PASSWORD", dotenv.get("DB_TEST_PASSWORD"));
    }

    @AfterAll
    static void tearDownAll() {
        System.clearProperty("DB_TEST_URL");
        System.clearProperty("DB_TEST_USERNAME");
        System.clearProperty("DB_TEST_PASSWORD");
    }
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_get_recipes_list_with_valid_idea_id_with_authorized_access() throws Exception {
        Integer idea_id = 1;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/ideas/{idea_id}/recipes", idea_id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").isArray()) // Ensure it's an array
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").isNotEmpty());
    }

    @Test
    public void test_get_recipes_list_with_invalid_idea_id_with_authorized_access() throws Exception {
        Integer idea_id = 2;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/ideas/{idea_id}/recipes", idea_id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").isArray()) // Ensure it's an array
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipes").isEmpty());
    }

    @Test
    public void test_get_recipes_details_with_valid_recipe_id_with_authorized_access() throws Exception {
        Integer recipe_id = 1;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/{recipe_id}", recipe_id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipe").exists())// Ensure it's an array
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipe").isNotEmpty());
    }


}