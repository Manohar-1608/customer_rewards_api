package com.rewardpoints.rewardscalculator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rewardpoints.rewardscalculator.rewardservice.RewardPointsService;
import com.rewardpoints.rewardscalculator.utils.RewardConstants;

@WebMvcTest(PointsCalculatorController.class)
public class PointsCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardPointsService rewardPointsService;

	public static final String MONTH_FORMAT = "yyyy-MM";


	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_FORMAT);

    //class ValidationTests {

	@Test
	@DisplayName("")
	void shouldReturnBadRequestForBlankCustomerId() throws Exception {
	    mockMvc.perform(get("/api/reward-points/count-points").param("customerId", "  ").param("from", "2024-05-01")
		    .param("to", "2024-06-01")).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("")
	void shouldReturnBadRequestForInvalidDateRange() throws Exception {
	    mockMvc.perform(get("/api/reward-points/count-points").param("customerId", "C1").param("from", "2024-07-01")
		    .param("to", "2024-06-01")).andExpect(status().isBadRequest());
	}

   // }
}
