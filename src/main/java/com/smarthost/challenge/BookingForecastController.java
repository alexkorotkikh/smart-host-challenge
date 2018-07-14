package com.smarthost.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingForecastController {
    private static final Logger log = LoggerFactory.getLogger(ChallengeApplication.class);

    @Autowired
    @Qualifier("guestBids")
    private List<Integer> guestBids;

    @GetMapping("/booking-forecast")
    public BookingForecastResult bookingForecast(final int freeEconomyRooms,
                                                 final int freePremiumRooms) {
        log.info(guestBids.toString());


        return null;
    }
}
