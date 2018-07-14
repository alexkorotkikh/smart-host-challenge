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
                        .param("availablePremiumRooms", "3")
                        .param("availableEconomyRooms", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumRoomsUsage", is(3)))
                .andExpect(jsonPath("$.economyRoomsUsage", is(3)))
                .andExpect(jsonPath("$.premiumRoomsIncome", is(738)))
                .andExpect(jsonPath("$.economyRoomsIncome", is(167)));
    }

    @Test
    public void bookingForecast_premiumBidsLack() throws Exception {
        mockMvc.perform(
                get("/booking-forecast")
                        .param("availablePremiumRooms", "7")
                        .param("availableEconomyRooms", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumRoomsUsage", is(6)))
                .andExpect(jsonPath("$.economyRoomsUsage", is(4)))
                .andExpect(jsonPath("$.premiumRoomsIncome", is(1054)))
                .andExpect(jsonPath("$.economyRoomsIncome", is(189)));
    }

    @Test
    public void bookingForecast_economyBidsLack() throws Exception {
        mockMvc.perform(
                get("/booking-forecast")
                        .param("availablePremiumRooms", "2")
                        .param("availableEconomyRooms", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumRoomsUsage", is(2)))
                .andExpect(jsonPath("$.economyRoomsUsage", is(4)))
                .andExpect(jsonPath("$.premiumRoomsIncome", is(583)))
                .andExpect(jsonPath("$.economyRoomsIncome", is(189)));
    }
}
