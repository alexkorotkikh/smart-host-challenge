package com.smarthost.challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingForecastResult {
    private int freeRoomsUsage;
    private int premiumRoomsUsage;
    private int freeRoomsIncome;
    private int premiumRoomsIncome;

    public BookingForecastResult() {
    }

    public int getFreeRoomsUsage() {
        return freeRoomsUsage;
    }

    public void setFreeRoomsUsage(int freeRoomsUsage) {
        this.freeRoomsUsage = freeRoomsUsage;
    }

    public int getPremiumRoomsUsage() {
        return premiumRoomsUsage;
    }

    public void setPremiumRoomsUsage(int premiumRoomsUsage) {
        this.premiumRoomsUsage = premiumRoomsUsage;
    }

    public int getFreeRoomsIncome() {
        return freeRoomsIncome;
    }

    public void setFreeRoomsIncome(int freeRoomsIncome) {
        this.freeRoomsIncome = freeRoomsIncome;
    }

    public int getPremiumRoomsIncome() {
        return premiumRoomsIncome;
    }

    public void setPremiumRoomsIncome(int premiumRoomsIncome) {
        this.premiumRoomsIncome = premiumRoomsIncome;
    }
}
