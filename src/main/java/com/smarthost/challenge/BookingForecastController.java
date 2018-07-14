package com.smarthost.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;

@RestController
public class BookingForecastController {
    private static final Logger log = LoggerFactory.getLogger(ChallengeApplication.class);
    private static final int THRESHOLD = 100;

    private List<Integer> guestBids;

    public BookingForecastController(@Autowired @Qualifier("guestBids") List<Integer> guestBids) {
        this.guestBids = guestBids;
    }

    @GetMapping("/booking-forecast")
    public BookingForecastResult bookingForecast(final int availableEconomyRooms,
                                                 final int availablePremiumRooms) {
        assert availablePremiumRooms >= 0;
        assert availableEconomyRooms >= 0;

        final Map<Boolean, List<Integer>> parts = guestBids.stream().collect(partitioningBy(bid -> bid >= THRESHOLD));
        final List<Integer> premiumBids = parts.get(true);
        final List<Integer> economyBids = parts.get(false);

        final int premiumRoomsUsage = Math.min(availablePremiumRooms, premiumBids.size());
        final int premiumRoomsIncome = premiumBids.stream().limit(premiumRoomsUsage).mapToInt(Integer::intValue).sum();
        final int premiumRoomsLeft = Math.max(availablePremiumRooms - premiumRoomsUsage, 0);

        final int economyRoomsShortage = Math.max(economyBids.size() - availableEconomyRooms, 0);
        final int upgradedRoomsUsage = Math.min(premiumRoomsLeft, economyRoomsShortage);
        final int upgradedRoomsIncome = economyBids.stream().limit(upgradedRoomsUsage).mapToInt(Integer::intValue).sum();

        final int economyRoomsUsage = Math.min(economyBids.size() - upgradedRoomsUsage, availableEconomyRooms);
        final int economyRoomsIncome = economyBids.stream().skip(upgradedRoomsUsage).limit(economyRoomsUsage).mapToInt(Integer::intValue).sum();

        return new BookingForecastResult(
                economyRoomsUsage, premiumRoomsUsage + upgradedRoomsUsage,
                economyRoomsIncome, premiumRoomsIncome + upgradedRoomsIncome
        );
    }
}

class BookingForecastResult {
    private int economyRoomsUsage;
    private int premiumRoomsUsage;
    private int economyRoomsIncome;
    private int premiumRoomsIncome;

    BookingForecastResult(final int economyRoomsUsage,
                          final int premiumRoomsUsage,
                          final int economyRoomsIncome,
                          final int premiumRoomsIncome) {
        this.economyRoomsUsage = economyRoomsUsage;
        this.premiumRoomsUsage = premiumRoomsUsage;
        this.economyRoomsIncome = economyRoomsIncome;
        this.premiumRoomsIncome = premiumRoomsIncome;
    }

    public int getEconomyRoomsUsage() {
        return economyRoomsUsage;
    }

    public int getPremiumRoomsUsage() {
        return premiumRoomsUsage;
    }

    public int getEconomyRoomsIncome() {
        return economyRoomsIncome;
    }

    public int getPremiumRoomsIncome() {
        return premiumRoomsIncome;
    }
}