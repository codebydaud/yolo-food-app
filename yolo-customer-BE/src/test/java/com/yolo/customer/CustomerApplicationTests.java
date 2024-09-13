package com.yolo.customer;

import org.junit.jupiter.api.*;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class CustomerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	public void LoginSuccess() throws Exception {
		String loginPayload = "{\"username\":\"FATIMA\",\"password\":\"12345\"}";

	}

	@BeforeEach
	public void setUp() {
		SecurityTestUtil.setJwtAuthenticationToken("admin",
				Set.of("VIEW_ORDER_HISTORY"),
				Map.of("preferred_username", "admin")
		);
	}

	@Order(1)
	@Test
	public void testGetOrderList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/orders?page=0&size=10")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders", Matchers.hasSize(Matchers.greaterThan(0))));
	}

	@Order(2)
	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_VIEW_ORDER_HISTORY"} )
	public void testGetOrderDetail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/orders/1/orderitems")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems", Matchers.notNullValue()));
	}

	@Order(3)
	@Test
	@WithMockUser(username = "admin", authorities = {"ROLE_UPDATE_ORDER_STATUS"} )
	public void testUpdateOrderStatus() throws Exception {
		String updatePayload = "{\"order_status\":\"DISPATCHED\"}";

		mockMvc.perform(MockMvcRequestBuilders.patch("/users/orders/ORD001")
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatePayload))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders.status").value("DISPATCHED"));
	}
}
