package com.yolo.customer;
import com.yolo.customer.address.AddressRepository;
import com.yolo.customer.order.OrderRepository;
import com.yolo.customer.order.OrderService;
import com.yolo.customer.order.orderItem.OrderItemRepository;
import com.yolo.customer.recipe.RecipeRepository;
import com.yolo.customer.user.UserRepository;
import com.yolo.customer.userProfile.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.SecurityTestUtil;

import java.util.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTests {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        SecurityTestUtil.setJwtAuthenticationToken("ahmad",
                Set.of("ROLE_PLACE_ORDER"),
                Map.of("preferred_username", "admin")
        );
    }

    @Order(1)
    @Test
    public void testPlaceOrder_Success() throws Exception {
        String orderRequestPayload = "{"
                + "\"order\": {"
                + "\"currencyCode\": \"USD\","
                + "\"orderItems\": [{"
                + "\"quantity\": 2,"
                + "\"price\": \"2388\","
                + "\"recipeId\": 1,"
                + "\"chefCode\": \"CHEF01\""
                + "}]"
                + "}"
                + "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Order(2)
    @Test
    public void testPlaceOrder_Failure_InvalidOrderItems() throws Exception {
        String orderRequestPayload = "{"
                + "\"order\": {"
                + "\"currencyCode\": \"USD\","
                + "\"orderItems\": []"
                + "}"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.details").value("Order items must not be empty."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.message").value("Bad Request"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCodeValue").value(400));
    }


    @Order(3)
    @Test
    public void testPlaceOrder_Failure_InvalidPrice() throws Exception {
        String orderRequestPayload = "{"
                + "\"order\": {"
                + "\"currencyCode\": \"USD\","
                + "\"orderItems\": [{"
                + "\"quantity\": 1,"
                + "\"price\": \"-260\","
                + "\"recipeId\": 1,"
                + "\"chefCode\": \"CHEF01\""
                + "}]"
                + "}"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.details").value("Price should not be less than 0."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.message").value("Bad Request"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCodeValue").value(400));
    }

    @Order(4)
    @Test
    public void testPlaceOrder_Failure_NullOrderDto() throws Exception {
        String orderRequestPayload = "{}";


        mockMvc.perform(MockMvcRequestBuilders.post("/users/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestPayload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.details").value("Order cannot be empty."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.message").value("Bad Request"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCodeValue").value(400));
    }
}
