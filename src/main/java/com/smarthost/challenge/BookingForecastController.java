package com.smarthost.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;

@RestController
public class BookingForecastController {
    private static final Logger log = LoggerFactory.getLogger(ChallengeApplication.class);
    private static final Integer THRESHOLD = 100;

    @Autowired
    @Qualifier("guestBids")
    private List<Integer> guestBids;

    @GetMapping("/booking-forecast")
    public BookingForecastResult bookingForecast(final int availableEconomyRooms,
                                                 final int availablePremiumRooms) {
        assert availablePremiumRooms >= 0;
        assert availableEconomyRooms >= 0;

        final BookingForecastResult result = new BookingForecastResult();

        final int premiumRoomsUsage =
                availablePremiumRooms > guestBids.size() ? guestBids.size() : availablePremiumRooms;
        final int premiumRoomsIncome =
                guestBids.stream().limit(premiumRoomsUsage).mapToInt(Integer::intValue).sum();
        result.setPremiumRoomsUsage(premiumRoomsUsage);
        result.setPremiumRoomsIncome(premiumRoomsIncome);

        final List<Integer> economyBids =
                guestBids.stream().skip(availableEconomyRooms).filter(bid -> bid < THRESHOLD)
                        .collect(Collectors.toList());
        final int economyRoomsUsage =
                availableEconomyRooms > economyBids.size() ? economyBids.size() : availableEconomyRooms;
        final int economyRoomsIncome =
                economyBids.stream().limit(availableEconomyRooms).mapToInt(Integer::intValue).sum();
        result.setEconomyRoomsUsage(economyRoomsUsage);
        result.setEconomyRoomsIncome(economyRoomsIncome);

        return result;
    }
}
