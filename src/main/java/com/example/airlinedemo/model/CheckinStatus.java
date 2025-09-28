package com.example.airlinedemo.model;

public enum CheckinStatus {
    PENDING("Check-in Available"),
    COMPLETED("Checked In");

    private final String displayName;

    CheckinStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
