package com.smarthost.challenge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingForecastResult {
    private int economyRoomsUsage;
    private int premiumRoomsUsage;
    private int economyRoomsIncome;
    private int premiumRoomsIncome;

    public BookingForecastResult() {
    }

    public int getEconomyRoomsUsage() {
        return economyRoomsUsage;
    }

    public void setEconomyRoomsUsage(int economyRoomsUsage) {
        this.economyRoomsUsage = economyRoomsUsage;
    }

    public int getPremiumRoomsUsage() {
        return premiumRoomsUsage;
    }

    public void setPremiumRoomsUsage(int premiumRoomsUsage) {
        this.premiumRoomsUsage = premiumRoomsUsage;
    }

    public int getEconomyRoomsIncome() {
        return economyRoomsIncome;
    }

    public void setEconomyRoomsIncome(int economyRoomsIncome) {
        this.economyRoomsIncome = economyRoomsIncome;
    }

    public int getPremiumRoomsIncome() {
        return premiumRoomsIncome;
    }

    public void setPremiumRoomsIncome(int premiumRoomsIncome) {
        this.premiumRoomsIncome = premiumRoomsIncome;
    }
}
