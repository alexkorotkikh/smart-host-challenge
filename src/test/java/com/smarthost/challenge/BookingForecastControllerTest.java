package com.smarthost.challenge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
public class BookingForecastControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void bookingForecast() throws Exception {
        mockMvc.perform(
                get("/booking-forecast")
                        .param("freeEconomyRooms", "3")
                        .param("freePremiumRooms", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.freeRoomsUsage", is(3)))
                .andExpect(jsonPath("$.premiumRoomsUsage", is(3)))
                .andExpect(jsonPath("$.freeRoomsIncome", is(3)))
                .andExpect(jsonPath("$.premiumRoomsIncome", is(3)));
    }
}
